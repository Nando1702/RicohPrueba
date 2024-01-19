package model;


import java.util.Date;

public class Registro {
	
	// ATRIBUTOS
	
	private Date bookingDate;
	
	private String email;
	
	private boolean activated;
	
	private Date activationDate;
	
	private Date entryDate;
	
	private String workcenter;
	
	private String workshift;
	
	// CONSTRUCTOR

	public Registro(Date bookingDate, String email, boolean activated, Date activationDate, Date entryDate,
			String workcenter, String workshift) {
		super();
		this.bookingDate = bookingDate;
		this.email = email;
		this.activated = activated;
		this.activationDate = activationDate;
		this.entryDate = entryDate;
		this.workcenter = workcenter;
		this.workshift = workshift;
	}
	
	// GETTERS Y SETTERS 

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public String getWorkshift() {
		return workshift;
	}

	public void setWorkshift(String workshift) {
		this.workshift = workshift;
	}

	@Override
	public String toString() {
		return "Registro [bookingDate=" + bookingDate + ", email=" + email + ", activated=" + activated
				+ ", activationDate=" + activationDate + ", entryDate=" + entryDate + ", workcenter=" + workcenter
				+ ", workshift=" + workshift + "]";
	}
	

}
