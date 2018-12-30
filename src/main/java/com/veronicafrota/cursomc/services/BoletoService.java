package com.veronicafrota.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.veronicafrota.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	//  Fills the Bill with the date seven days 
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);		// add seven days 
		pagto.setDataVencimento(cal.getTime());
	}

}
