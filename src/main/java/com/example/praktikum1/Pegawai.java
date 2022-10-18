/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.praktikum1;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER DJOGJA
 */
@Entity
@Table(name = "pegawai")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pegawai.findAll", query = "SELECT p FROM Pegawai p"),
    @NamedQuery(name = "Pegawai.findByIdpegawai", query = "SELECT p FROM Pegawai p WHERE p.idpegawai = :idpegawai"),
    @NamedQuery(name = "Pegawai.findByNama", query = "SELECT p FROM Pegawai p WHERE p.nama = :nama"),
    @NamedQuery(name = "Pegawai.findByNohp", query = "SELECT p FROM Pegawai p WHERE p.nohp = :nohp"),
    @NamedQuery(name = "Pegawai.findByJenisKelamin", query = "SELECT p FROM Pegawai p WHERE p.jenisKelamin = :jenisKelamin")})
public class Pegawai implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpegawai")
    private Integer idpegawai;
    @Basic(optional = false)
    @Column(name = "nama")
    private String nama;
    @Basic(optional = false)
    @Column(name = "nohp")
    private String nohp;
    @Basic(optional = false)
    @Column(name = "jenis kelamin")
    private String jenisKelamin;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idpegawai")
    private Transaksi transaksi;

    public Pegawai() {
    }

    public Pegawai(Integer idpegawai) {
        this.idpegawai = idpegawai;
    }

    public Pegawai(Integer idpegawai, String nama, String nohp, String jenisKelamin) {
        this.idpegawai = idpegawai;
        this.nama = nama;
        this.nohp = nohp;
        this.jenisKelamin = jenisKelamin;
    }

    public Integer getIdpegawai() {
        return idpegawai;
    }

    public void setIdpegawai(Integer idpegawai) {
        this.idpegawai = idpegawai;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpegawai != null ? idpegawai.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pegawai)) {
            return false;
        }
        Pegawai other = (Pegawai) object;
        if ((this.idpegawai == null && other.idpegawai != null) || (this.idpegawai != null && !this.idpegawai.equals(other.idpegawai))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.praktikum1.Pegawai[ idpegawai=" + idpegawai + " ]";
    }
    
}
