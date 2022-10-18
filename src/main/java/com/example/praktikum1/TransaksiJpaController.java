/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.praktikum1;

import com.example.praktikum1.exceptions.IllegalOrphanException;
import com.example.praktikum1.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER DJOGJA
 */
public class TransaksiJpaController implements Serializable {

    public TransaksiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaksi transaksi) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Barang idbarangOrphanCheck = transaksi.getIdbarang();
        if (idbarangOrphanCheck != null) {
            Transaksi oldTransaksiOfIdbarang = idbarangOrphanCheck.getTransaksi();
            if (oldTransaksiOfIdbarang != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Barang " + idbarangOrphanCheck + " already has an item of type Transaksi whose idbarang column cannot be null. Please make another selection for the idbarang field.");
            }
        }
        Pembeli idpembeliOrphanCheck = transaksi.getIdpembeli();
        if (idpembeliOrphanCheck != null) {
            Transaksi oldTransaksiOfIdpembeli = idpembeliOrphanCheck.getTransaksi();
            if (oldTransaksiOfIdpembeli != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Pembeli " + idpembeliOrphanCheck + " already has an item of type Transaksi whose idpembeli column cannot be null. Please make another selection for the idpembeli field.");
            }
        }
        Pegawai idpegawaiOrphanCheck = transaksi.getIdpegawai();
        if (idpegawaiOrphanCheck != null) {
            Transaksi oldTransaksiOfIdpegawai = idpegawaiOrphanCheck.getTransaksi();
            if (oldTransaksiOfIdpegawai != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Pegawai " + idpegawaiOrphanCheck + " already has an item of type Transaksi whose idpegawai column cannot be null. Please make another selection for the idpegawai field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barang idbarang = transaksi.getIdbarang();
            if (idbarang != null) {
                idbarang = em.getReference(idbarang.getClass(), idbarang.getIdbarang());
                transaksi.setIdbarang(idbarang);
            }
            Pembeli idpembeli = transaksi.getIdpembeli();
            if (idpembeli != null) {
                idpembeli = em.getReference(idpembeli.getClass(), idpembeli.getIdpembeli());
                transaksi.setIdpembeli(idpembeli);
            }
            Pegawai idpegawai = transaksi.getIdpegawai();
            if (idpegawai != null) {
                idpegawai = em.getReference(idpegawai.getClass(), idpegawai.getIdpegawai());
                transaksi.setIdpegawai(idpegawai);
            }
            em.persist(transaksi);
            if (idbarang != null) {
                idbarang.setTransaksi(transaksi);
                idbarang = em.merge(idbarang);
            }
            if (idpembeli != null) {
                idpembeli.setTransaksi(transaksi);
                idpembeli = em.merge(idpembeli);
            }
            if (idpegawai != null) {
                idpegawai.setTransaksi(transaksi);
                idpegawai = em.merge(idpegawai);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaksi transaksi) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi persistentTransaksi = em.find(Transaksi.class, transaksi.getIdtransaksi());
            Barang idbarangOld = persistentTransaksi.getIdbarang();
            Barang idbarangNew = transaksi.getIdbarang();
            Pembeli idpembeliOld = persistentTransaksi.getIdpembeli();
            Pembeli idpembeliNew = transaksi.getIdpembeli();
            Pegawai idpegawaiOld = persistentTransaksi.getIdpegawai();
            Pegawai idpegawaiNew = transaksi.getIdpegawai();
            List<String> illegalOrphanMessages = null;
            if (idbarangNew != null && !idbarangNew.equals(idbarangOld)) {
                Transaksi oldTransaksiOfIdbarang = idbarangNew.getTransaksi();
                if (oldTransaksiOfIdbarang != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Barang " + idbarangNew + " already has an item of type Transaksi whose idbarang column cannot be null. Please make another selection for the idbarang field.");
                }
            }
            if (idpembeliNew != null && !idpembeliNew.equals(idpembeliOld)) {
                Transaksi oldTransaksiOfIdpembeli = idpembeliNew.getTransaksi();
                if (oldTransaksiOfIdpembeli != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Pembeli " + idpembeliNew + " already has an item of type Transaksi whose idpembeli column cannot be null. Please make another selection for the idpembeli field.");
                }
            }
            if (idpegawaiNew != null && !idpegawaiNew.equals(idpegawaiOld)) {
                Transaksi oldTransaksiOfIdpegawai = idpegawaiNew.getTransaksi();
                if (oldTransaksiOfIdpegawai != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Pegawai " + idpegawaiNew + " already has an item of type Transaksi whose idpegawai column cannot be null. Please make another selection for the idpegawai field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idbarangNew != null) {
                idbarangNew = em.getReference(idbarangNew.getClass(), idbarangNew.getIdbarang());
                transaksi.setIdbarang(idbarangNew);
            }
            if (idpembeliNew != null) {
                idpembeliNew = em.getReference(idpembeliNew.getClass(), idpembeliNew.getIdpembeli());
                transaksi.setIdpembeli(idpembeliNew);
            }
            if (idpegawaiNew != null) {
                idpegawaiNew = em.getReference(idpegawaiNew.getClass(), idpegawaiNew.getIdpegawai());
                transaksi.setIdpegawai(idpegawaiNew);
            }
            transaksi = em.merge(transaksi);
            if (idbarangOld != null && !idbarangOld.equals(idbarangNew)) {
                idbarangOld.setTransaksi(null);
                idbarangOld = em.merge(idbarangOld);
            }
            if (idbarangNew != null && !idbarangNew.equals(idbarangOld)) {
                idbarangNew.setTransaksi(transaksi);
                idbarangNew = em.merge(idbarangNew);
            }
            if (idpembeliOld != null && !idpembeliOld.equals(idpembeliNew)) {
                idpembeliOld.setTransaksi(null);
                idpembeliOld = em.merge(idpembeliOld);
            }
            if (idpembeliNew != null && !idpembeliNew.equals(idpembeliOld)) {
                idpembeliNew.setTransaksi(transaksi);
                idpembeliNew = em.merge(idpembeliNew);
            }
            if (idpegawaiOld != null && !idpegawaiOld.equals(idpegawaiNew)) {
                idpegawaiOld.setTransaksi(null);
                idpegawaiOld = em.merge(idpegawaiOld);
            }
            if (idpegawaiNew != null && !idpegawaiNew.equals(idpegawaiOld)) {
                idpegawaiNew.setTransaksi(transaksi);
                idpegawaiNew = em.merge(idpegawaiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaksi.getIdtransaksi();
                if (findTransaksi(id) == null) {
                    throw new NonexistentEntityException("The transaksi with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi transaksi;
            try {
                transaksi = em.getReference(Transaksi.class, id);
                transaksi.getIdtransaksi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaksi with id " + id + " no longer exists.", enfe);
            }
            Barang idbarang = transaksi.getIdbarang();
            if (idbarang != null) {
                idbarang.setTransaksi(null);
                idbarang = em.merge(idbarang);
            }
            Pembeli idpembeli = transaksi.getIdpembeli();
            if (idpembeli != null) {
                idpembeli.setTransaksi(null);
                idpembeli = em.merge(idpembeli);
            }
            Pegawai idpegawai = transaksi.getIdpegawai();
            if (idpegawai != null) {
                idpegawai.setTransaksi(null);
                idpegawai = em.merge(idpegawai);
            }
            em.remove(transaksi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaksi> findTransaksiEntities() {
        return findTransaksiEntities(true, -1, -1);
    }

    public List<Transaksi> findTransaksiEntities(int maxResults, int firstResult) {
        return findTransaksiEntities(false, maxResults, firstResult);
    }

    private List<Transaksi> findTransaksiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaksi.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Transaksi findTransaksi(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaksi.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaksiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaksi> rt = cq.from(Transaksi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
