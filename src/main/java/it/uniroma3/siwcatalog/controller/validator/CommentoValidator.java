package it.uniroma3.siwcatalog.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siwcatalog.model.Commento;
import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.repository.CommentiRepository;

@Component
public class CommentoValidator implements Validator{

    @Autowired
    private CommentiRepository commentiRepository;


    @Override
	public boolean supports(Class<?> aClass) {
		return Prodotto.class.equals(aClass);
	}

        @Override
        public void validate(Object target, Errors errors) {
            Commento commento = (Commento) target;
            if (commento.getTitolo()!=null && commento.getTesto()!=null && commento.getAutore()!=null && 
                this.commentiRepository.existsByTitoloAndTestoAndAutore(commento.getTitolo(), commento.getTesto(), commento.getAutore())) {
                errors.reject("commento.duplicato");
            }
        }

    
}
