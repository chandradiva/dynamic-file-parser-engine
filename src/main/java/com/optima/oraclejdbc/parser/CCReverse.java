package com.optima.oraclejdbc.parser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.model.CCAccountBalance;
import com.optima.oraclejdbc.model.CCAccountHeader;
import com.optima.oraclejdbc.model.CCAccountLevelMessage;
import com.optima.oraclejdbc.model.CCAccountTrailer;
import com.optima.oraclejdbc.model.CCCustomerHeader;
import com.optima.oraclejdbc.model.CCCustomerHeaderMessage;
import com.optima.oraclejdbc.model.CCCustomerTrailer;
import com.optima.oraclejdbc.model.CCDualCurrency;
import com.optima.oraclejdbc.model.CCFileTrailer;
import com.optima.oraclejdbc.model.CCFooterMessage;
import com.optima.oraclejdbc.model.CCLuckyDraw;
import com.optima.oraclejdbc.model.CCParentModel;
import com.optima.oraclejdbc.model.CCPromotionalMessage;
import com.optima.oraclejdbc.model.CCRateInfo;
import com.optima.oraclejdbc.model.CCRewardSummary;
import com.optima.oraclejdbc.model.CCStatement;
import com.optima.oraclejdbc.model.CCStatementMessage;
import com.optima.oraclejdbc.model.CCTransactionDetails;

@Service
public class CCReverse extends NISPReverse<CCParentModel> {

	@Override
	public List<String> reverse(List<CCParentModel> datas, String filename) {
		List<String> rd = new ArrayList<String>();
		
		try {
			for (CCParentModel item : datas) {
				String cc = "";
				
				if (item instanceof CCStatement) {
					cc = reverse(item, CCStatement.class);
				} else if (item instanceof CCCustomerHeader) {
					cc = reverse(item, CCCustomerHeader.class);
				} else if (item instanceof CCAccountLevelMessage) {
					cc = reverse(item, CCAccountLevelMessage.class);
				} else if (item instanceof CCAccountHeader) {
					cc = reverse(item, CCAccountHeader.class);
				} else if (item instanceof CCTransactionDetails) {
					cc = reverse(item, CCTransactionDetails.class);
				} else if (item instanceof CCLuckyDraw) {
					cc = reverse(item, CCLuckyDraw.class);
				} else if (item instanceof CCAccountBalance) {
					cc = reverse(item, CCAccountBalance.class);
				} else if (item instanceof CCRewardSummary) {
					cc = reverse(item, CCRewardSummary.class);
				} else if (item instanceof CCRateInfo) {
					cc = reverse(item, CCRateInfo.class);
				} else if (item instanceof CCStatementMessage) {
					cc = reverse(item, CCStatementMessage.class);
				} else if (item instanceof CCPromotionalMessage) {
					cc = reverse(item, CCPromotionalMessage.class);
				} else if (item instanceof CCAccountTrailer) {
					cc = reverse(item, CCAccountTrailer.class);
				} else if (item instanceof CCCustomerHeaderMessage) {
					cc = reverse(item, CCCustomerHeaderMessage.class);
				} else if (item instanceof CCCustomerTrailer) {
					cc = reverse(item, CCCustomerTrailer.class);
				} else if (item instanceof CCDualCurrency) {
					cc = reverse(item, CCDualCurrency.class);
				} else if (item instanceof CCFileTrailer) {
					cc = reverse(item, CCFileTrailer.class);
				} else if (item instanceof CCFooterMessage) {
					cc = reverse(item, CCFooterMessage.class);
				}
				
				rd.add(cc);
			}
			
			return rd;
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}

	@Override
	public List<String> reverseObj(Object[] datas, List<String> alamats, String filename) {
		List<String> rd = new ArrayList<String>();
		
		try {
			for (int i = 0; i < datas.length; i++) {
				String cc = "";
				
				if (datas[i] instanceof CCStatement) {
					cc = reverseObj(datas[i], CCStatement.class);
				} else if (datas[i] instanceof CCCustomerHeader) {
					((CCCustomerHeader) datas[i]).setMailingAddressLine1(alamats.get(0));
					((CCCustomerHeader) datas[i]).setMailingAddressLine2(alamats.get(1));
					((CCCustomerHeader) datas[i]).setMailingAddressLine3(alamats.get(2));
					((CCCustomerHeader) datas[i]).setMailingAddressLine4(alamats.get(3));
					
					cc = reverseObj(datas[i], CCCustomerHeader.class);
				} else if (datas[i] instanceof CCAccountLevelMessage) {
					cc = reverseObj(datas[i], CCAccountLevelMessage.class);
				} else if (datas[i] instanceof CCAccountHeader) {
//					((CCAccountHeader) datas[i]).setMailingAddressLine1(alamats.get(0));
//					((CCAccountHeader) datas[i]).setMailingAddressLine2(alamats.get(1));
//					((CCAccountHeader) datas[i]).setMailingAddressLine3(alamats.get(2));
//					((CCAccountHeader) datas[i]).setMailingAddressLine4(alamats.get(3));
					
					cc = reverseObj(datas[i], CCAccountHeader.class);
				} else if (datas[i] instanceof CCTransactionDetails) {
					cc = reverseObj(datas[i], CCTransactionDetails.class);
				} else if (datas[i] instanceof CCLuckyDraw) {
					cc = reverseObj(datas[i], CCLuckyDraw.class);
				} else if (datas[i] instanceof CCAccountBalance) {
					cc = reverseObj(datas[i], CCAccountBalance.class);
				} else if (datas[i] instanceof CCRewardSummary) {
					cc = reverseObj(datas[i], CCRewardSummary.class);
				} else if (datas[i] instanceof CCRateInfo) {
					cc = reverseObj(datas[i], CCRateInfo.class);
				} else if (datas[i] instanceof CCStatementMessage) {
					cc = reverseObj(datas[i], CCStatementMessage.class);
				} else if (datas[i] instanceof CCPromotionalMessage) {
					cc = reverseObj(datas[i], CCPromotionalMessage.class);
				} else if (datas[i] instanceof CCAccountTrailer) {
					cc = reverseObj(datas[i], CCAccountTrailer.class);
				} else if (datas[i] instanceof CCCustomerHeaderMessage) {
					cc = reverseObj(datas[i], CCCustomerHeaderMessage.class);
				} else if (datas[i] instanceof CCCustomerTrailer) {
					cc = reverseObj(datas[i], CCCustomerTrailer.class);
				} else if (datas[i] instanceof CCDualCurrency) {
					cc = reverseObj(datas[i], CCDualCurrency.class);
				} else if (datas[i] instanceof CCFileTrailer) {
					cc = reverseObj(datas[i], CCFileTrailer.class);
				} else if (datas[i] instanceof CCFooterMessage) {
					cc = reverseObj(datas[i], CCFooterMessage.class);
				}
				
				rd.add(cc);
			}
			
			return rd;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
