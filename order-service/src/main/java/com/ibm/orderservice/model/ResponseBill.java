/**
 * 
 */
package com.ibm.orderservice.model;

import java.util.List;
/**
 * @author RavikumarPothannagar
 *
 */
public class ResponseBill {


	private List<ResponseOrder> orderlist;
	int totalAmount;
	public int getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	private String Status;
	private String StatusMsg;
	

	public ResponseBill() {
		
	}
	
	
	public ResponseBill(List<ResponseOrder> orderlist, String status,String statusMsg) {
		super();
		this.orderlist = orderlist;
		Status = status;
		StatusMsg = statusMsg;
	}



	public List<ResponseOrder> getOrderlist() {
		return orderlist;
	}


	public void setOrderlist(List<ResponseOrder> orderlist) {
		this.orderlist = orderlist;
	}


	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getStatusMsg() {
		return StatusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		StatusMsg = statusMsg;
	}
}
