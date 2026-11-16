package com.optima.oraclejdbc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.optima.oraclejdbc.api.ReturnData;
import com.optima.oraclejdbc.dummy.model.DummyBancassurance;
import com.optima.oraclejdbc.dummy.model.DummyCreditCard;
import com.optima.oraclejdbc.dummy.model.DummyCurrentAccount;
import com.optima.oraclejdbc.dummy.model.DummyLoan;
import com.optima.oraclejdbc.dummy.model.DummyMultiCurrency;
import com.optima.oraclejdbc.dummy.model.DummyPoint;
import com.optima.oraclejdbc.dummy.model.DummySavingAccount;
import com.optima.oraclejdbc.dummy.model.DummySavingsPlan;
import com.optima.oraclejdbc.dummy.model.DummyTimeDeposit;
import com.optima.oraclejdbc.dummy.model.DummyUnitTrust;

@RestController
@RequestMapping(value = "/api/dummy")
public class ApiDummyController {

	@RequestMapping(value = "getdummyunittrust", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReturnData getDummyUnitTrust() {
		ReturnData rd = new ReturnData();
		
		try {
			List<DummyUnitTrust> listUt = new ArrayList<DummyUnitTrust>();
			DummyUnitTrust ut1 = new DummyUnitTrust();
			DummyUnitTrust ut2 = new DummyUnitTrust();
			
			ut1.setProductName("Reksa Dana Terpadu");
			ut1.setCurrency("IDR");
			ut1.setAccountNo("SPI-5551123");
			ut1.setOutstandingUnits("1,000");
			ut1.setNav("0.8961");
			ut1.setEstimatedAmount("983.99");
			
			ut2.setProductName("Reksa Dana Valas");
			ut2.setCurrency("USD");
			ut2.setAccountNo("SPI-5551123");
			ut2.setOutstandingUnits("1,000");
			ut2.setNav("0.8961");
			ut2.setEstimatedAmount("992.00");
			
			listUt.add(ut1);
			listUt.add(ut2);
			
			rd.success = true;
			rd.resultCode = 1000;
			rd.data = listUt;
		} catch (Exception e) {
			rd.message = e.getMessage();
			rd.success = false;
		}
		
		return rd;
	}
	
	@RequestMapping(value = "getdummybancass", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReturnData getDummyBancass() {
		ReturnData rd = new ReturnData();
		
		try {
			List<DummyBancassurance> listBancass = new ArrayList<DummyBancassurance>();
			DummyBancassurance bancass1 = new DummyBancassurance();
			DummyBancassurance bancass2 = new DummyBancassurance();
			
			bancass1.setProductName("Live Insurance AXA");
			bancass1.setCurrency("IDR");
			bancass1.setAccountNo("010810234563");
			bancass1.setPolicyNo("AA-234849-724");
			bancass1.setStatus("OPEN");
			bancass1.setPremium("10,832,992.00");
			bancass1.setTotalSumInsured("10,832,992.00");
			bancass1.setLastPayment("01/05/2015");
			
			bancass2.setProductName("Live Insurance AXA Overseas");
			bancass2.setCurrency("USD");
			bancass2.setAccountNo("010810234563");
			bancass2.setPolicyNo("AA-234849-724");
			bancass2.setStatus("OPEN");
			bancass2.setPremium("2,992.00");
			bancass2.setTotalSumInsured("2,992.00");
			bancass2.setLastPayment("01/05/2015");
			
			listBancass.add(bancass1);
			listBancass.add(bancass2);
			
			rd.success = true;
			rd.resultCode = 1000;
			rd.data = listBancass;
		} catch (Exception e) {
			rd.message = e.getMessage();
			rd.success = false;
		}
		
		return rd;
	}
	
	@RequestMapping(value = "getdummycreditcard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReturnData getDummyCreditCard() {
		ReturnData rd = new ReturnData();
		
		try {
			List<DummyCreditCard> listCc = new ArrayList<DummyCreditCard>();
			DummyCreditCard cc1 = new DummyCreditCard();
			DummyCreditCard cc2 = new DummyCreditCard();
			
			cc1.setProductName("Platinum OCBC NISP");
			cc1.setCurrency("IDR");
			cc1.setAccountNo("010810234563");
			cc1.setPoints("2129");
			cc1.setExpireDate("10/2016");
			cc1.setAvailableLimit("10,832,992.00");
			cc1.setCreditLimit("2,982,981");
			cc1.setOutstandingBalance("10,832,992.00");
			cc1.setLastBilled("6,974,652.00");
			cc1.setMinimumPayment("500,000.00");
			cc1.setDueDate("01/05/2015");
			
			cc2.setProductName("Titanium OCBC NISP");
			cc2.setCurrency("IDR");
			cc2.setAccountNo("010810234563");
			cc2.setPoints("5129");
			cc2.setExpireDate("10/2016");
			cc2.setAvailableLimit("10,832,992.00");
			cc2.setCreditLimit("2,982,981");
			cc2.setOutstandingBalance("10,832,992.00");
			cc2.setLastBilled("774,656.00");
			cc2.setMinimumPayment("500,000.00");
			cc2.setDueDate("01/05/2015");
			
			listCc.add(cc1);
			listCc.add(cc2);
			
			rd.success = true;
			rd.resultCode = 1000;
			rd.data = listCc;
		} catch (Exception e) {
			rd.message = e.getMessage();
			rd.success = false;
		}
		
		return rd;
	}
	
	@RequestMapping(value = "getdummycurrentaccount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReturnData getDummyCurrentAccount() {
		ReturnData rd = new ReturnData();
		
		try {
			List<DummyCurrentAccount> listCa = new ArrayList<DummyCurrentAccount>();
			DummyCurrentAccount ca1 = new DummyCurrentAccount();
			DummyCurrentAccount ca2 = new DummyCurrentAccount();
			
			ca1.setProductName("Giro Perorangan");
			ca1.setCurrency("IDR");
			ca1.setAccountNo("010810234563");
			ca1.setBalance("11,332,990.00");
			ca1.setAvailable("10,832,992.00");
			ca1.setHold("500,000.00");
			ca1.setOverdraft("400,000.00");
			
			ca2.setProductName("Giro VALAS");
			ca2.setCurrency("USD");
			ca2.setAccountNo("010810234563");
			ca2.setBalance("1,000.00");
			ca2.setAvailable("900.00");
			ca2.setHold("100.00");
			ca2.setOverdraft("50.00");
			
			listCa.add(ca1);
			listCa.add(ca2);
			
			rd.success = true;
			rd.resultCode = 1000;
			rd.data = listCa;
		} catch (Exception e) {
			rd.message = e.getMessage();
			rd.success = false;
		}
		
		return rd;
	}
	
	@RequestMapping(value = "getdummymcy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReturnData getDummyMultiCurrency() {
		ReturnData rd = new ReturnData();
		
		try {
			List<DummyMultiCurrency> listMcy = new ArrayList<DummyMultiCurrency>();
			DummyMultiCurrency mcy1 = new DummyMultiCurrency();
			DummyMultiCurrency mcy2 = new DummyMultiCurrency();
			DummyMultiCurrency mcy3 = new DummyMultiCurrency();
			DummyMultiCurrency mcy4 = new DummyMultiCurrency();
			DummyMultiCurrency mcy5 = new DummyMultiCurrency();
			
			mcy1.setProductName("Multicurrency");
			mcy1.setCurrency("AUD");
			mcy1.setAccountNo("010810234563");
			mcy1.setBalance("1,000.00");
			mcy1.setAvailable("800.00");
			mcy1.setHold("300.00");
			mcy1.setOverdraft("100.00");
			
			mcy2.setProductName("Multicurrency");
			mcy2.setCurrency("USD");
			mcy2.setAccountNo("010810234563");
			mcy2.setBalance("2,000.00");
			mcy2.setAvailable("900.00");
			mcy2.setHold("400.00");
			mcy2.setOverdraft("200.00");
			
			mcy3.setProductName("Multicurrency");
			mcy3.setCurrency("SGD");
			mcy3.setAccountNo("010810234563");
			mcy3.setBalance("3,000.00");
			mcy3.setAvailable("1.000.00");
			mcy3.setHold("500.00");
			mcy3.setOverdraft("300.00");
			
			mcy4.setProductName("Multicurrency");
			mcy4.setCurrency("YEN");
			mcy4.setAccountNo("010810234563");
			mcy4.setBalance("100,000.00");
			mcy4.setAvailable("80.000.00");
			mcy4.setHold("30.000.00");
			mcy4.setOverdraft("10.000.00");
			
			mcy5.setProductName("Multicurrency");
			mcy5.setCurrency("EUR");
			mcy5.setAccountNo("010810234563");
			mcy5.setBalance("500.00");
			mcy5.setAvailable("400.00");
			mcy5.setHold("200.00");
			mcy5.setOverdraft("100.00");
			
			listMcy.add(mcy1);
			listMcy.add(mcy2);
			listMcy.add(mcy3);
			listMcy.add(mcy4);
			listMcy.add(mcy5);
			
			rd.success = true;
			rd.resultCode = 1000;
			rd.data = listMcy;
		} catch (Exception e) {
			rd.message = e.getMessage();
			rd.success = false;
		}
		
		return rd;
	}
	
	@RequestMapping(value = "getdummypoint", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReturnData getDummyPoint() {
		ReturnData rd = new ReturnData();
		
		try {
			List<DummyPoint> listPoint = new ArrayList<DummyPoint>();
			DummyPoint point1 = new DummyPoint();
			DummyPoint point2 = new DummyPoint();
			
			point1.setProductName("Reward Points");
			point1.setAccountNo("010810234563");
			point1.setBalance("1,000");
			point1.setExpireDate("10/2/2029");
			
			point2.setProductName("Lucky Draw");
			point2.setAccountNo("010810234563");
			point2.setBalance("500");
			point2.setExpireDate("10/2/2029");
			
			listPoint.add(point1);
			listPoint.add(point2);
			
			rd.success = true;
			rd.resultCode = 1000;
			rd.data = listPoint;
		} catch (Exception e) {
			rd.message = e.getMessage();
			rd.success = false;
		}
		
		return rd;
	}
	
	@RequestMapping(value = "getdummysavingaccount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReturnData getDummySavingAccount() {
		ReturnData rd = new ReturnData();
		
		try {
			List<DummySavingAccount> listSa = new ArrayList<DummySavingAccount>();
			DummySavingAccount sa1 = new DummySavingAccount();
			DummySavingAccount sa2 = new DummySavingAccount();
			
			sa1.setProductName("TANDA 360");
			sa1.setCurrency("IDR");
			sa1.setAccountNo("010810234563");
			sa1.setBalance("11,332,990.00");
			sa1.setAvailable("10,832,992.00");
			sa1.setHold("500,000.00");
			
			sa2.setProductName("TANDA Valas");
			sa2.setCurrency("USD");
			sa2.setAccountNo("010810234563");
			sa2.setBalance("1,000.00");
			sa2.setAvailable("900.00");
			sa2.setHold("100.00");
			
			listSa.add(sa1);
			listSa.add(sa2);
			
			rd.success = true;
			rd.resultCode = 1000;
			rd.data = listSa;
		} catch (Exception e) {
			rd.message = e.getMessage();
			rd.success = false;
		}
		
		return rd;
	}
	
	@RequestMapping(value = "getdummysavingsplan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReturnData getDummySavingsPlan() {
		ReturnData rd = new ReturnData();
		
		try {
			List<DummySavingsPlan> listSp = new ArrayList<DummySavingsPlan>();
			DummySavingsPlan sp1 = new DummySavingsPlan();
			DummySavingsPlan sp2 = new DummySavingsPlan();
			
			sp1.setProductName("TAKA Merdeka");
			sp1.setCurrency("IDR");
			sp1.setAccountNo("010810234563");
			sp1.setBalance("10,832,992.00");
			sp1.setTargetAmount("10,832,992.00");
			sp1.setPaymentAmount("288.461");
			sp1.setDueDate("01/05/2015");
			sp1.setEffectiveDate("12/01/2016");
			sp1.setTenorMonth("12");
			sp1.setInterest("4.1247%");
			
			sp2.setProductName("TAKA Valas");
			sp2.setCurrency("USD");
			sp2.setAccountNo("010810234563");
			sp2.setBalance("100.00");
			sp2.setTargetAmount("1,000.00");
			sp2.setPaymentAmount("26.3");
			sp2.setDueDate("01/05/2015");
			sp2.setEffectiveDate("12/01/2016");
			sp2.setTenorMonth("12");
			sp2.setInterest("4.1247%");
			
			listSp.add(sp1);
			listSp.add(sp2);
			
			rd.success = false;
			rd.resultCode = 1000;
			rd.data = listSp;
		} catch (Exception e) {
			rd.message = e.getMessage();
			rd.success = false;
		}
		
		return rd;
	}
	
	@RequestMapping(value = "getdummytimedeposit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReturnData getDummyTimeDeposit() {
		ReturnData rd = new ReturnData();
		
		try {
			List<DummyTimeDeposit> listTd = new ArrayList<DummyTimeDeposit>();
			DummyTimeDeposit td1 = new DummyTimeDeposit();
			DummyTimeDeposit td2 = new DummyTimeDeposit();
			
			td1.setProductName("Deposito 3 Bulan");
			td1.setCurrency("IDR");
			td1.setAccountNo("010810234563");
			td1.setReceiptNo("000012");
			td1.setEffectiveDate("01/05/2015");
			td1.setMaturityDate("01/08/2015");
			td1.setPrincipal("500,000.00");
			td1.setInterestAmount("4,777,127.00");
			td1.setTenor("3 Bulan");
			td1.setInterest("8.00%");
			
			td2.setProductName("Deposito Valas 3 Bulan");
			td2.setCurrency("USD");
			td2.setAccountNo("010810234563");
			td2.setReceiptNo("000013");
			td2.setEffectiveDate("01/05/2015");
			td2.setMaturityDate("01/08/2015");
			td2.setPrincipal("1,000.00");
			td2.setInterestAmount("4,7");
			td2.setTenor("3 Bulan");
			td2.setInterest("8.00%");
			
			listTd.add(td1);
			listTd.add(td2);
			
			rd.success = false;
			rd.resultCode = 1000;
			rd.data = listTd;
		} catch (Exception e) {
			rd.message = e.getMessage();
			rd.success = false;
		}
		
		return rd;
	}
	
	@RequestMapping(value = "getdummyloan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ReturnData getDummyLoan() {
		ReturnData rd = new ReturnData();
		
		try {
			List<DummyLoan> listLoan = new ArrayList<DummyLoan>();
			DummyLoan loan1 = new DummyLoan();
			DummyLoan loan2 = new DummyLoan();
			
			loan1.setProductName("Housing Loan");
			loan1.setCurrency("IDR");
			loan1.setAccountNo("010810234563");
			loan1.setOutstandingAmount("10,832,992.00");
			loan1.setMonthlyPayment("10,832,992.00");
			loan1.setDueDate("01/05/2015");
			loan1.setOverdueAmount("10,832,992.00");
			
			loan2.setProductName("Car Loan");
			loan2.setCurrency("USD");
			loan2.setAccountNo("010810234563");
			loan2.setOutstandingAmount("900.00");
			loan2.setMonthlyPayment("832,992.00");
			loan2.setDueDate("01/05/2015");
			loan2.setOverdueAmount("92.00");
			
			listLoan.add(loan1);
			listLoan.add(loan2);
			
			rd.success = true;
			rd.resultCode = 1000;
			rd.data = listLoan;
		} catch (Exception e) {
			rd.message = e.getMessage();
			rd.success = false;
		}
		
		return rd;
	}
}
