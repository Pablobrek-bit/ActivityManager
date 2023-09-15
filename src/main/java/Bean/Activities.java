package Bean;

import java.sql.Date;
import java.time.LocalDate;

//method for storing activity elements
public class Activities {
	private int id;
	private String title;
	private String description;
	private Date data_create;
	private Date final_date;
	private String status;
	private int user_id;
	
	public Activities() {
		super();
		this.data_create = Date.valueOf(LocalDate.now());
		this.status = "in_progress";
	}

	public Date getFinal_date() {
		return final_date;
	}

	public void setFinal_date(Date final_date) {
		this.final_date = final_date;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getData_create() {
		return data_create;
	}

	public void setData_create(Date data_create) {
		this.data_create = data_create;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Activities [title=" + title + ", description=" + description + ", data_create=" + data_create
				+ ", final_date=" + final_date + ", status=" + status + "]";
	}
	
	
}
