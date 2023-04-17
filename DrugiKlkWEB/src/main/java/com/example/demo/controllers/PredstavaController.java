package com.example.demo.controllers;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.GlumiUizvodjenjuRepo;
import com.example.demo.repository.IzvodjenjeRepo;
import com.example.demo.repository.KartaRepo;
import com.example.demo.repository.PredstavaRepo;
import com.example.demo.repository.ReziserRepo;
import com.example.demo.repository.ScenaRepo;
import com.example.demo.repository.ZanrPredstaveRepo;
import com.example.demo.repository.ZanrRepo;

import model.GlumiUIzvodjenju;
import model.Izvodjenje;
import model.Karta;
import model.Predstava;
import model.Reziser;
import model.Scena;
import model.Zanr;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@EntityScan("model")
@RequestMapping(value = "/predstave")
public class PredstavaController {

	@Autowired
	PredstavaRepo pr;

	@Autowired
	IzvodjenjeRepo ir;

	@Autowired
	KartaRepo kr;

	@Autowired
	ReziserRepo rr;

	@Autowired
	ScenaRepo sr;

	@Autowired
	GlumiUizvodjenjuRepo guir;

	@Autowired
	ZanrRepo zr;

	@Autowired
	ZanrPredstaveRepo zpr;

	@GetMapping(value = "/izlistajScene")
	public String izlistajScene(HttpServletRequest req) {
		List<Scena> s = sr.findAll();
		req.getSession().setAttribute("scene", s);

		return "prikazIzvodjenjaScene";
	}

	@GetMapping(value = "/getIzvodjenjaScene")
	public String getIzvodjenjaScene(HttpServletRequest req, Integer idScena) {

		Scena s = sr.findById(idScena).get();

		List<Izvodjenje> i = ir.findByScena(s);

		req.getSession().setAttribute("scena", s);

		req.getSession().setAttribute("izvodjenja", i);

		return "prikazIzvodjenjaScene";
	}

	@GetMapping(value = "/getUloge")
	public void getUloge(HttpServletResponse response, Integer idIzvodjenje) throws Exception {

		Izvodjenje i = ir.findById(idIzvodjenje).get();

		List<GlumiUIzvodjenju> guizv = guir.findByIzvodjenje(i);

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(guizv);

		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/prikazUlogaZaIzvodjenje.jrxml");

		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("reziser",
				i.getPredstava().getReziser().getIme() + " " + i.getPredstava().getReziser().getPrezime());

		params.put("naziv", i.getPredstava().getNaziv());

		params.put("opis", i.getPredstava().getOpis());

		params.put("trajanje", i.getPredstava().getTrajanje());

		List<Zanr> zps = zr.findByPredstava(i.getPredstava().getIdPredstava());
		String zanroviString = "";
		for (Zanr z : zps) {
			zanroviString += z.getNaziv() + ", ";
		}
		zanroviString = zanroviString.substring(0, zanroviString.length() - 2);
		params.put("zanrovi", zanroviString);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

		inputStream.close();

		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=prikazUlogaZaIzvodjenje.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);

	}

	@GetMapping(value = "/izlistajRezisere")
	public String izlistajRezisere(HttpServletRequest req) {
		List<Reziser> reziseri = rr.findAll();
		req.getSession().setAttribute("reziseri", reziseri);
		return "prikazPredstavaRezisera";
	}

	@GetMapping(value = "/getPredstaveZaRezisera")
	public String findPredstavaByReziser(HttpServletRequest req, Integer idReziser) {

		Reziser r = rr.findById(idReziser).get();
		List<Predstava> predstave = pr.findByReziser(r);
		req.getSession().setAttribute("predstave", predstave);
		req.getSession().setAttribute("reziser", r.getIme() + " " + r.getPrezime());
		return "prikazPredstavaRezisera";
	}

	@GetMapping(value = "/getIzvestaj")
	public void izgenerisiIzvestaj(HttpServletResponse response, HttpServletRequest request) throws Exception {

		List<Predstava> predstave = (List<Predstava>) request.getSession().getAttribute("predstave");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(predstave);
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/prikazPredstava.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("reziser",
				predstave.get(0).getReziser().getIme() + " " + predstave.get(0).getReziser().getPrezime());

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

		inputStream.close();

		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=prikazPredstava.pdf");

		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	}

	@GetMapping(value = "/izlistajPredstave")
	public String izlistajPredstave(HttpServletRequest req) {

		List<Predstava> predstave = pr.findAll();

		req.getSession().setAttribute("predstave", predstave);

		return "prikazIzvodjenja";

	}

	@GetMapping(value = "/getPredstava")
	public String getPredstava(String naziv, HttpServletRequest req) {

		Predstava predstava = pr.findByNaziv(naziv);

		List<Izvodjenje> izvodjenja = ir.findByPredstava(predstava);

		req.getSession().setAttribute("izvodjenja", izvodjenja);
		req.getSession().setAttribute("predstava", naziv);
		return "prikazIzvodjenja";
	}

	@GetMapping(value = "/vratiKarteZaIzvodjenje")
	public void vratiKarteZaIzvodjenje(Integer idIzvodjenja, HttpServletResponse response) throws Exception {

		Izvodjenje i = ir.findById(idIzvodjenja).get();

		List<Karta> karte = kr.findByIzvodjenje(i);

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(karte);
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/prikazKarata.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("predstava", i.getPredstava().getNaziv());
		params.put("scena", i.getScena().getNaziv());

		params.put("datumIzv", i.getDatum());

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

		inputStream.close();

		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=prikazKarata.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	}

}
