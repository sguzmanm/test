/*
MIT License

Copyright (c) 2017 ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.cities.persistence;

import co.edu.uniandes.csw.cities.entities.CityEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ISIS2603
 */
@Stateless
public class CityPersistence {

    private static final Logger LOGGER = Logger.getLogger(CityPersistence.class.getName());

    @PersistenceContext(unitName = "CityPU")
    protected EntityManager em;

    /**
     *
     * @param entity objeto city que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public CityEntity create(CityEntity entity) {
        LOGGER.info("Creando una city nueva");
        em.persist(entity);
        LOGGER.info("Creando una city nueva");
        return entity;
    }

    /**
     * Busca si hay alguna city con el nombre que se envía de argumento
     *
     * @param name: Nombre de la city que se está buscando
     * @return null si no existe ninguna city con el nombre del argumento. Si
     * existe alguna devuelve la primera.
     */
    public CityEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando city por nombre ", name);

        // Se crea un query para buscar cityes con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From CityEntity e where e.name = :name", CityEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<CityEntity> sameName = query.getResultList();
        if (sameName.isEmpty()) {
            return null;
        } else {
            return sameName.get(0);
        }
    }

    public List<CityEntity> findAll() {
        LOGGER.info("Consultando todas las cities");
        TypedQuery query = em.createQuery("select u from CityEntity u", CityEntity.class);
        return query.getResultList();
    }
    
   public CityEntity update(CityEntity entity)
    {
        LOGGER.info("Actualizando info de "+entity.getName());
        em.merge(entity);
        return entity;
    }
    
}
