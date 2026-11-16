package com.optima.oraclejdbc.model.estatement;

import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.NISPEntityNumberFormat;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "ES_DDYMOW")
public class ESDdymow {

	@NISPEntityId
	@NISPEntity(columnName = "ID")
	private Long id;
	
	@NISPEntity(columnName = "PAYMENT_STATUS")
	@Position(start = 0, length = 1)
	private String paymentStatus;
	
	@NISPEntity(columnName = "WAIVE_IND")
	@Position(start = 1, length = 1)
	private String waiveInd;
	
	@NISPEntity(columnName = "SEQUENCE_PAY")
	@Position(start = 2, length = 4)
	private Double sequencePay;
	
	@NISPEntity(columnName = "ACCOUNT_NUMBER")
	@Position(start = 6, length = 20)
	private String accountNumber;
	
	@NISPEntity(columnName = "ACCOUNT_TYPE")
	@Position(start = 26, length = 1)
	private String accountType;
	
	@NISPEntity(columnName = "TRANSACTION_CODE")
	@Position(start = 27, length = 4)
	private String transactionCode;
	
	@NISPEntity(columnName = "REVERSE_TRN_CODE")
	@Position(start = 31, length = 4)
	private String reverseTrnCode;
	
	@NISPEntity(columnName = "AMOUNT_OWING")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 35, length = 19)
	private Double amountOwing;
	
	@NISPEntity(columnName = "AMOUNT_PAID")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 54, length = 19)
	private Double amountPaid;
	
	@NISPEntity(columnName = "AMOUNT_LAST_PAID")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 73, length = 19)
	private Double amountLastPaid;
	
	@NISPEntity(columnName = "OWING_DATE_1")
	@Position(start = 92, length = 8)
	private Date owingDate1;
	
	@NISPEntity(columnName = "OWING_DATE_2")
	@Position(start = 100, length = 8)
	private Date owingDate2;
	
	@NISPEntity(columnName = "FULL_PAYMENT_DATE_1")
	@Position(start = 108, length = 8)
	private Date fullPaymentDate1;
	
	@NISPEntity(columnName = "FULL_PAYMENT_DATE_2")
	@Position(start = 116, length = 8)
	private Date fullPaymentDate2;
	
	@NISPEntity(columnName = "LAST_PAID_DATE_1")
	@Position(start = 124, length = 8)
	private Date LastPaidDate1;
	
	@NISPEntity(columnName = "LAST_PAID_DATE_2")
	@Position(start = 132, length = 8)
	private Date lastPaidDate2;
	
	@NISPEntity(columnName = "WAIVE_DATE_1")
	@Position(start = 140, length = 8)
	private Date waiveDate1;
	
	@NISPEntity(columnName = "WAIVE_DATE_2")
	@Position(start = 148, length = 8)
	private Date waiveDate2;
	
	@NISPEntity(columnName = "DATE_MAINTAINED_1")
	@Position(start = 156, length = 8)
	private Date dateMaintained1;
	
	@NISPEntity(columnName = "DATE_MAINTAINED_2")
	@Position(start = 164, length = 8)
	private Date dateMaintained2;
	
	@NISPEntity(columnName = "USER_ID")
	@Position(start = 172, length = 10)
	private String userId;
	
	@NISPEntity(columnName = "TRANSACTION_REMARK")
	@Position(start = 182, length = 40)
	private String transactionRemark;
	
	@NISPEntity(columnName = "LAST_CHANGED_BY_USER")
	@Position(start = 222, length = 10)
	private String lastChangedByUser;
	
	@NISPEntity(columnName = "LAST_CHANGED_1")
	@Position(start = 232, length = 8)
	private String lastChanged1;
	
	@NISPEntity(columnName = "LAST_CHANGED_2")
	@Position(start = 240, length = 8)
	private String lastChanged2;
	
	@NISPEntity(columnName = "LAST_CHANGED_TIME")
	@Position(start = 248, length = 8)
	private String lastChangedTime;
	
}
