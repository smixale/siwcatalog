package it.uniroma3.siwcatalog.controller.validator;

import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.repository.ProdottoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProdottoValidator implements Validator{

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Override
	public boolean supports(Class<?> aClass) {
		return Prodotto.class.equals(aClass);
	}

    @Override
    public void validate(Object target, Errors errors) {
        Prodotto prodotto = (Prodotto) target;
        if (prodotto.getNomeProdotto()!=null && prodotto.getPrezzo()!=null) {
            if (this.prodottoRepository.existsByNomeProdottoAndPrezzo(prodotto.getNomeProdotto(), prodotto.getPrezzo())) {
                errors.reject("prodotto.duplicato");
            }
            if (prodotto.getPrezzo()<0) {
                errors.reject("prodotto.costoNegativo");
            }
        }
    }
}
