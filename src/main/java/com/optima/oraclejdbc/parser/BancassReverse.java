package com.optima.oraclejdbc.parser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.model.BADetailBiaya;
import com.optima.oraclejdbc.model.BADetailPremi;
import com.optima.oraclejdbc.model.BADisclaimer;
import com.optima.oraclejdbc.model.BAParentModel;
import com.optima.oraclejdbc.model.BATransactionSummary;
import com.optima.oraclejdbc.model.BATransaksiLain;

@Service
public class BancassReverse extends NISPReverse<BAParentModel> {

	@Override
	public List<String> reverse(List<BAParentModel> datas, String filename) {
		List<String> rd = new ArrayList<String>();
		
		try {
			for (BAParentModel item : datas) {
				String bancass = "";
				
				if (item instanceof BATransactionSummary) {
					bancass = reverse(item, BATransactionSummary.class);
				} else if (item instanceof BADetailPremi) {
					bancass = reverse(item, BADetailPremi.class);
				} else if (item instanceof BADetailBiaya) {
					bancass = reverse(item, BADetailBiaya.class);
				} else if (item instanceof BATransaksiLain) {
					bancass = reverse(item, BATransaksiLain.class);
				} else if (item instanceof BADisclaimer) {
					bancass = reverse(item, BADisclaimer.class);
				}
				
				rd.add(bancass);
			}
			return rd;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<String> reverseObj(Object[] datas, List<String> alamats,
			String filename) {
		// TODO Auto-generated method stub
		return null;
	}

}
