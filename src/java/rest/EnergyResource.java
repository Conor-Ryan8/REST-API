package rest;

import data.EnergyService;
import data.EnergyReading;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/Readings")
public class EnergyResource 
{
    @Context
    private UriInfo context;
    
    private EnergyService energyService = new EnergyService();
    
    public EnergyResource()
    { 
    }
     
    @HEAD
    public Response head()   
    {
        return Response.noContent().status(Response.Status.NO_CONTENT).build();
    }
    
    @OPTIONS
    public Response options()
    {
        Set<String> options = new TreeSet<>();
        options.add("HEAD");
        options.add("GET");
        options.add("DELETE"); 
        options.add("POST");
        return Response.noContent().allow(options).build();     
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAll()
    {
        List<EnergyReading> readings = energyService.getAllReadings();   
        GenericEntity<List<EnergyReading>> all = new 
        GenericEntity<List<EnergyReading>>(readings){};          
        return Response.status(Response.Status.OK).entity(all).build();
    }
          
    @DELETE
    public Response deleteAll()
    {
        energyService.deleteAllReadings();     
        return Response.noContent().build();     
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response add(EnergyReading reading)
    {
        boolean status = energyService.addReading(reading);          
        if (status == true)
        {
            return Response.status(Response.Status.OK).
                    entity(reading).build();
        }
        else
        {
            return Response.status(Response.Status.CONFLICT).
                    entity("<Reading Conflict/>").build();
        }
    }          
    
    @HEAD
    @Path("{time}")
    public Response readingHead()
    {
        return Response.noContent().status(Response.Status.NO_CONTENT).build();
    }
    
    @OPTIONS
    @Path("{time}")
    public Response readingOptions()
    {
        Set<String> options = new TreeSet<>();
        options.add("HEAD");
        options.add("GET");
        options.add("DELETE"); 
        options.add("PUT");     
        return Response.noContent().allow(options).build();      
    }
     
    @GET
    @Path("{time}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response get( @PathParam("time") String time)
    {
        EnergyReading reading = energyService.getReading(time);
        if(reading == null)
        {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("<readingNotFound />")
                    .build();
        }
        else
        {
            return Response
                    .status(Response.Status.OK)
                    .entity(reading)
                    .build();
        }       
    } 
    
    @DELETE
    @Path("{time}")
    public Response delete(@PathParam("time") String time)
    {
        energyService.deleteReading(time);     
        return Response.noContent().build();     
    }
    
    @PUT
    @Path("{time}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response edit(EnergyReading reading, 
            @PathParam("time") String time)
    {
        if(time.equals(reading.getTime()))
        {
            if(energyService.getReading(time) == null)
            {
                energyService.addReading(reading);
                return Response
                        .status(Response.Status.CREATED)
                        .header("Location", context.getAbsolutePath().toString())
                        .entity(reading)
                        .build();
            }
            else
            {
                energyService.updateReading(reading);
                return Response
                        .status(Response.Status.OK)
                        .entity(reading)
                        .build();
            }           
        }
        else
        {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity("<Error />")
                    .build();
        }
    }   
}