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
public class PegawaiJpaController implements Serializable {

    public PegawaiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pegawai pegawai) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi transaksi = pegawai.getTransaksi();
            if (transaksi != null) {
                transaksi = em.getReference(transaksi.getClass(), transaksi.getIdtransaksi());
                pegawai.setTransaksi(transaksi);
            }
            em.persist(pegawai);
            if (transaksi != null) {
                Pegawai oldIdpegawaiOfTransaksi = transaksi.getIdpegawai();
                if (oldIdpegawaiOfTransaksi != null) {
                    oldIdpegawaiOfTransaksi.setTransaksi(null);
                    oldIdpegawaiOfTransaksi = em.merge(oldIdpegawaiOfTransaksi);
                }
                transaksi.setIdpegawai(pegawai);
                transaksi = em.merge(transaksi);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pegawai pegawai) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pegawai persistentPegawai = em.find(Pegawai.class, pegawai.getIdpegawai());
            Transaksi transaksiOld = persistentPegawai.getTransaksi();
            Transaksi transaksiNew = pegawai.getTransaksi();
            List<String> illegalOrphanMessages = null;
            if (transaksiOld != null && !transaksiOld.equals(transaksiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Transaksi " + transaksiOld + " since its idpegawai field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (transaksiNew != null) {
                transaksiNew = em.getReference(transaksiNew.getClass(), transaksiNew.getIdtransaksi());
                pegawai.setTransaksi(transaksiNew);
            }
            pegawai = em.merge(pegawai);
            if (transaksiNew != null && !transaksiNew.equals(transaksiOld)) {
                Pegawai oldIdpegawaiOfTransaksi = transaksiNew.getIdpegawai();
                if (oldIdpegawaiOfTransaksi != null) {
                    oldIdpegawaiOfTransaksi.setTransaksi(null);
                    oldIdpegawaiOfTransaksi = em.merge(oldIdpegawaiOfTransaksi);
                }
                transaksiNew.setIdpegawai(pegawai);
                transaksiNew = em.merge(transaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pegawai.getIdpegawai();
                if (findPegawai(id) == null) {
                    throw new NonexistentEntityException("The pegawai with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pegawai pegawai;
            try {
                pegawai = em.getReference(Pegawai.class, id);
                pegawai.getIdpegawai();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pegawai with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Transaksi transaksiOrphanCheck = pegawai.getTransaksi();
            if (transaksiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pegawai (" + pegawai + ") cannot be destroyed since the Transaksi " + transaksiOrphanCheck + " in its transaksi field has a non-nullable idpegawai field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pegawai);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pegawai> findPegawaiEntities() {
        return findPegawaiEntities(true, -1, -1);
    }

    public List<Pegawai> findPegawaiEntities(int maxResults, int firstResult) {
        return findPegawaiEntities(false, maxResults, firstResult);
    }

    private List<Pegawai> findPegawaiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pegawai.class));
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

    public Pegawai findPegawai(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pegawai.class, id);
        } finally {
            em.close();
        }
    }

    public int getPegawaiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pegawai> rt = cq.from(Pegawai.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
