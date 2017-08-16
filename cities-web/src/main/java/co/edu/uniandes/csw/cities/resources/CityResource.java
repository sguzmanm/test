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
package co.edu.uniandes.csw.cities.resources;

import co.edu.uniandes.csw.cities.ejb.CityLogic;

import co.edu.uniandes.csw.cities.dtos.CityDetailDTO;
import co.edu.uniandes.csw.cities.entities.CityEntity;
import co.edu.uniandes.csw.cities.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Clase que implementa el recurso REST correspondiente a "cities".
 *
 * Note que la aplicación (definida en RestConfig.java) define la ruta "/api" y
 * este recurso tiene la ruta "cities". Al ejecutar la aplicación, el recurso
 * será accesibe a través de la ruta "/api/cities"
 *
 * @author ISIS2603
 *
 */
@Path("cities")
@Produces("application/json")
@Stateless
public class CityResource {

    @Inject
    CityLogic cityLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @POST
    public CityDetailDTO createCity(CityDetailDTO city) throws BusinessLogicException {
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CityEntity cityEntity = city.toEntity();
        // Invoca la lógica para crear la city nueva
        CityEntity nuevoCity = cityLogic.createCity(cityEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        return new CityDetailDTO(nuevoCity);
    }

    
    
    public List<CityDetailDTO> getCities() throws BusinessLogicException {
        return listEntity2DetailDTO(cityLogic.getCities());
    }
    @GET
    @Path("{id: \\d+}")
    public CityDetailDTO getCity(@PathParam("id")Long id)
    {
        CityDetailDTO c=null;
        for(CityEntity e:cityLogic.getCities())
        {
            if(e.getId().equals(id)) 
            {
                c=new CityDetailDTO(e);
                break;
            }
        }
        return c;
    }
    
    
    private List<CityDetailDTO> listEntity2DetailDTO(List<CityEntity> entityList) {
        List<CityDetailDTO> list = new ArrayList<>();
        for (CityEntity entity : entityList) {
            list.add(new CityDetailDTO(entity));
        }
        return list;
    }
    
    
    @PUT
    @PathParam("{id: \\d+}")
    public CityDetailDTO updateCity(@PathParam("id")Long id,CityDetailDTO city) throws BusinessLogicException
    {
        System.out.println("AAAA "+city);
        if(city==null)return null;
        CityEntity entity=city.toEntity();
        CityDetailDTO temp=new CityDetailDTO(cityLogic.update(id, entity));
        return temp;
    }
    
    
    /*
    @GET
    @Path("{name: [a-zA-Z]}")
    public CityDetailDTO getCity(@PathParam("name")String name)
    {
        CityDetailDTO c=null;
        for(CityEntity e:cityLogic.getCities())
        {
            if(e.getName().equals(name)) 
            {
                c=new CityDetailDTO(e);
                break;
            }
        }
        return c;
    }
    */
}
