package com.optima.oraclejdbc.response.dailystatement;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailtimeddeposit.RsSubTDAccountInfo_type0;

public class TDAResponse implements Serializable {

	private static final long serialVersionUID = 203531385991303963L;
	private String accountNo; //rekening
	private String tdaDate; //tanggal
	private String tdaName; //nama
	private String tdaLocation; //lokasi
	private String tdaAddress; //alamat
	private String tenor; //waktu
	private String valutaDate; //awal
	private String maturityDate; //akhir
	private String currency; //uang
	private String principalAmount; //nominal
	private String interestRate; //bunga persen
	private String words; //bilang
	private String maturity; //tempo
	private String interestAmount; //bunga
	private String taxes; //pajak
	private String netInterest; //jumlah
	private String logo; //logo
	
	public TDAResponse(RsSubTDAccountInfo_type0 detail) {
		String xml = detail.getTDAdviceXML();
		xml = xml.replaceAll("&lt;", "<");
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			
			Document doc = builder.parse(is);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("dataaccount");
			Node node = nList.item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				
				accountNo = element.getElementsByTagName("rekening").item(0).getTextContent();
				tdaDate = element.getElementsByTagName("tanggal").item(0).getTextContent();
				tdaName = element.getElementsByTagName("nama").item(0).getTextContent();
				tdaLocation = element.getElementsByTagName("lokasi").item(0).getTextContent();
				tdaAddress = element.getElementsByTagName("alamat").item(0).getTextContent();
				tenor = element.getElementsByTagName("waktu").item(0).getTextContent();
				valutaDate = element.getElementsByTagName("awal").item(0).getTextContent();
				maturityDate = element.getElementsByTagName("akhir").item(0).getTextContent();
				currency = element.getElementsByTagName("uang").item(0).getTextContent();
				principalAmount = element.getElementsByTagName("nominal").item(0).getTextContent();
				interestRate = element.getElementsByTagName("bunga_persen").item(0).getTextContent();
				words = element.getElementsByTagName("bilang").item(0).getTextContent();
				maturity = element.getElementsByTagName("tempo").item(0).getTextContent();
				interestAmount = element.getElementsByTagName("bunga").item(0).getTextContent();
				taxes = element.getElementsByTagName("pajak").item(0).getTextContent();
				netInterest = element.getElementsByTagName("jumlah").item(0).getTextContent();
				logo = element.getElementsByTagName("logo").item(0).getTextContent();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getTdaDate() {
		return tdaDate;
	}
	public void setTdaDate(String tdaDate) {
		this.tdaDate = tdaDate;
	}
	public String getTdaName() {
		return tdaName;
	}
	public void setTdaName(String tdaName) {
		this.tdaName = tdaName;
	}
	public String getTdaLocation() {
		return tdaLocation;
	}
	public void setTdaLocation(String tdaLocation) {
		this.tdaLocation = tdaLocation;
	}
	public String getTdaAddress() {
		return tdaAddress;
	}
	public void setTdaAddress(String tdaAddress) {
		this.tdaAddress = tdaAddress;
	}
	public String getTenor() {
		return tenor;
	}
	public void setTenor(String tenor) {
		this.tenor = tenor;
	}
	public String getValutaDate() {
		return valutaDate;
	}
	public void setValutaDate(String valutaDate) {
		this.valutaDate = valutaDate;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(String principalAmount) {
		this.principalAmount = principalAmount;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public String getMaturity() {
		return maturity;
	}
	public void setMaturity(String maturity) {
		this.maturity = maturity;
	}
	public String getInterestAmount() {
		return interestAmount;
	}
	public void setInterestAmount(String interestAmount) {
		this.interestAmount = interestAmount;
	}
	public String getTaxes() {
		return taxes;
	}
	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}
	public String getNetInterest() {
		return netInterest;
	}
	public void setNetInterest(String netInterest) {
		this.netInterest = netInterest;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
}
