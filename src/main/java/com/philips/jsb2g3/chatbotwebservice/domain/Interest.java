package com.philips.jsb2g3.chatbotwebservice.domain;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "interest")
public class Interest {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "sno")
    int sno;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "name")
    Monitor monitor;

    @Column(name = "idate")
    Date date;

    @Transient
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    java.util.Date idate;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public java.util.Date getIdate() {
        return idate;
    }

    public void setIdate(java.util.Date idate) {
        this.idate = idate;
        this.date = new Date(idate.getTime());
    }
    
}