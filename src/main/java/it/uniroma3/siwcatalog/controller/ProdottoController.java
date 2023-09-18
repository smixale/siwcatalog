package it.uniroma3.siwcatalog.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siwcatalog.controller.validator.ProdottoValidator;
import it.uniroma3.siwcatalog.model.Commento;
import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.service.FornitoreService;
import it.uniroma3.siwcatalog.service.ProdottoService;
import jakarta.validation.Valid;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;






@Controller
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private ProdottoValidator prodottoValidator;

    @Autowired
    private FornitoreService fornitoreService;

    @Autowired
    private GlobalController globalController;

    @GetMapping("/formNewProdotto")
    public String getFormNewProdotto(Model model) {
        model.addAttribute("prodotto", new Prodotto());
        return "/formNewProdotto.html";
    }
    
    @PostMapping("/prodotto")
    public String getProdotto(@Valid @ModelAttribute("prodotto") Prodotto prodotto, BindingResult bindingResult, Model model, @ModelAttribute("file") MultipartFile immagine) throws IOException {
        this.prodottoValidator.validate(prodotto, bindingResult);
        if (!bindingResult.hasErrors()) {
            model.addAttribute("prodotto", this.prodottoService.creaProdotto(prodotto, immagine));
            model.addAttribute("commentato", this.prodottoService.commentato(prodotto.getId()));
            model.addAttribute("user", this.globalController.getUser());
            model.addAttribute("commento", new Commento());
            return "prodotto.html";
        }else{
            model.addAttribute("messaggio", "Qualcosa è andato storto");
            return "erroreProdotto.html";
        }
    }

        /* mapping dall'index per la lista di tutti i prodotti */
    @GetMapping("/listaProdotti")
    public String getListaFornitori(Model model){
        model.addAttribute("prodotti",  this.prodottoService.findAllProdotti());
        return "listaProdotti.html";
    }

   /* mapping da la pagina listaFornitori per il prodotto nel dettaglio */
   @GetMapping("/prodotto/{id}")
	public String getStrategia(@PathVariable("id") Long id, Model model) {
		Prodotto prodotto= this.prodottoService.findProdottoById(id);
        model.addAttribute("prodotto", prodotto);
        model.addAttribute("commentato", this.prodottoService.commentato(id));
        model.addAttribute("user", this.globalController.getUser());
        model.addAttribute("commento", new Commento());
		return "prodotto.html";
	}

    @GetMapping(value="/formUpdateProdotto/{id}")
    public String formUpdateProdotto (@PathVariable("id") Long id, Model model) {
        Prodotto prodotto = this.prodottoService.findProdottoById(id);

        model.addAttribute("prodotto", prodotto);
        model.addAttribute("fornitori", this.prodottoService.fornitoriDaAggiungere(id));

        return "formUpdateProdotto.html";
    }

    @GetMapping("/addFornitore/{prodottoId}/{fornitoreId}")
    public String addFornitore (@PathVariable("prodottoId") Long prodottoId, @PathVariable("fornitoreId") Long fornitoreId, Model model) {

        this.fornitoreService.addProdotto(fornitoreId, prodottoId);

        model.addAttribute("prodotto", this.prodottoService.addFornitore(prodottoId, fornitoreId));
        model.addAttribute("fornitori", this.prodottoService.fornitoriDaAggiungere(prodottoId));

        return "formUpdateProdotto.html";
    }

    @GetMapping(value="/removeFornitore/{prodottoId}/{fornitoreId}")
    public String removeFornitore (@PathVariable("prodottoId") Long prodottoId, @PathVariable("fornitoreId") Long fornitoreId, Model model) {

        this.fornitoreService.removeProdotto(fornitoreId, prodottoId);

        model.addAttribute("prodotto", this.prodottoService.removeFornitore(prodottoId, fornitoreId));
        model.addAttribute("fornitori", this.prodottoService.fornitoriDaAggiungere(prodottoId));
        
        return "formUpdateProdotto.html";
    }

    @GetMapping(value="/deleteProdotto/{id}")
    public String rimuoviFornitore(@PathVariable("id") Long id, Model model) {
        if (!(this.prodottoService.existsById(id))) {
            model.addAttribute("messaggio", "Qualcosa è andato storto");
            return "erroreProdotto.html";
        }
        this.prodottoService.deleteProdotto(id);
        model.addAttribute("prodotti",  this.prodottoService.findAllProdotti());
        return "listaProdotti.html";
    }

    @GetMapping("/aggiornaProdotto/{id}")
    public String getMethodName(Model model, @PathVariable("id") Long id)  {
        model.addAttribute("vecchioProdotto", this.prodottoService.findProdottoById(id));
        model.addAttribute("nuovoProdotto", new Prodotto());
        return "formAggiornProdotto.html";
    }
    
    @PostMapping("/aggiornaProdotto/{id}")
    public String postMethodName(@Valid @ModelAttribute("nuovoProdotto") Prodotto nuovo, BindingResult bindingResult, Model model, @ModelAttribute("file") MultipartFile immagine, @PathVariable("id") Long id) throws IOException {
        
        this.prodottoValidator.validate(nuovo, bindingResult);

        if (!bindingResult.hasErrors()) {
            model.addAttribute("prodotto", this.prodottoService.aggiornaProdotto(id, nuovo.getNomeProdotto(),nuovo.getPrezzo(),nuovo.getDescrizione(), immagine));
            model.addAttribute("commentato", this.prodottoService.commentato(id));
            model.addAttribute("user", this.globalController.getUser());
            model.addAttribute("commento", new Commento());
            return "prodotto.html";
        }else{
            model.addAttribute("messaggio", "Dati inseriti non validi");
            return "erroreProdotto.html";
        }
    }
}
