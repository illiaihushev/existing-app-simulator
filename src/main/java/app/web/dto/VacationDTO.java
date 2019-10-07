package app.web.dto;

import app.model.dao.VacationDAO;

import java.sql.Date;

public class VacationDTO {
    private Date startDate;
    private Date endDate;

    public VacationDTO(VacationDAO vacation) {
        this.startDate = vacation.getStartDate();
        this.endDate = vacation.getEndDate();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
