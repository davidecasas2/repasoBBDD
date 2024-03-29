/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.ConexionBD;
import modelo.Pelicula;


/**
 * @author David
 * Clase que implementa un CRUD de la base batos
 * (Create, Read, update y delete)
 */
public class PeliculaDAO {

	private ConexionBD conexion;
	
    public PeliculaDAO() {
        this.conexion = new ConexionBD();
    }


    public ArrayList<Pelicula> obtenerPelicula() {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		Statement consulta = null;
		ResultSet resultado = null;
		ArrayList<Pelicula> lista = new ArrayList<Pelicula>();
		
		try {
			consulta = con.createStatement();
			resultado = consulta.executeQuery("select * from pelicula");
			
			// Bucle para recorrer todas las filas que devuelve la consulta
			while(resultado.next()) {
				int idPelicula = resultado.getInt("id_pelicula");
				String titulo = resultado.getString("titulo");
				String descripcion = resultado.getString("descripcion");
				int anyoLanzamiento = resultado.getInt("anyo_lanzamiento");
				int idIdioma = resultado.getInt("id_idioma");
				int idIdiomaOriginal = resultado.getInt("id_idioma_original");
				int duracionAlquiler = resultado.getInt("duracion_alquiler");
				float rentalRate = resultado.getFloat("rental_rate");
				int duracion = resultado.getInt("duracion");
				float replacementCost =resultado.getFloat("replacement_cost");
				
				
				Pelicula p = new Pelicula(idPelicula, titulo, descripcion, anyoLanzamiento, idIdioma, idIdiomaOriginal, duracionAlquiler, rentalRate, duracion, replacementCost);
				lista.add(p);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al realizar la consulta sobre autores: "+e.getMessage());
		} finally {
			try {
				resultado.close();
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return lista;
    }
/*
    public ArrayList<Autor> obtenerAutores(Libro l) {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		Statement consulta = null;
		ResultSet resultado = null;
		ArrayList<Autor> lista = new ArrayList<Autor>();
		
		try {
			consulta = con.createStatement();
			resultado = consulta.executeQuery("select a.idAutor, nombre, apellidos\r\n"
					+ "from autores a inner join autorlibro al\r\n"
					+ "on a.idAutor = al.idAutor\r\n"
					+ "where isbn = ?;");
			
			// Bucle para recorrer todas las filas que devuelve la consulta
			while(resultado.next()) {
				int idAutor = resultado.getInt("idAutor");
				String nombre = resultado.getString("nombre");
				String ape = resultado.getString("apellidos");
				
				Autor au = new Autor(idAutor, nombre,ape);
				lista.add(au);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al realizar la consulta de autores de un libro: "+e.getMessage());
		} finally {
			try {
				resultado.close();
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return lista;
    }

    public Autor obtenerAutor(int idAutor) {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		PreparedStatement consulta = null;
		ResultSet resultado = null;
		Autor au=null;
		
		try {
			consulta = con.prepareStatement("select * from autores"
					+ " where idAutor = ?");
			consulta.setInt(1, idAutor);
			resultado=consulta.executeQuery();
			
			// Bucle para recorrer todas las filas que devuelve la consulta
			if (resultado.next()) {
				String nombre = resultado.getString("nombre");
				String ape = resultado.getString("apellidos");
				
				au = new Autor(idAutor, nombre,ape);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al realizar la consulta sobre autores: "
		         +e.getMessage());
		} finally {
			try {
				resultado.close();
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return au;
    }


    public int insertarAutor(Autor autor) {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		PreparedStatement consulta = null;
		int resultado=0;
		
		try {
			consulta = con.prepareStatement("INSERT INTO autores (nombre,apellidos)"
					+ " VALUES (?,?) ");
			
			consulta.setString(1, autor.getNombre());
			consulta.setString(2, autor.getApellidos());
			resultado=consulta.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error al realizar la inserción del autor: "
		        +e.getMessage());
		} finally {
			try {
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return resultado;
    }

    public int actualizarAutor(Autor autor) {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		PreparedStatement consulta = null;
		int resultado=0;
		
		try {
			consulta = con.prepareStatement("UPDATE autor \r\n"
					+ "SET `nombre` = ?, `apellidos` = ?\r\n"
					+ "WHERE `idAutor` = ?;");
			
			consulta.setString(1, autor.getNombre());
			consulta.setString(2, autor.getApellidos());
			consulta.setInt(3, autor.getIdAutor());
			resultado=consulta.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error al realizar la actualizacion de autores: "
					+e.getMessage());
		} finally {
			try {
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return resultado;
    }


    public int eliminarAutores(Autor autor) {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		PreparedStatement consulta = null;
		int resultado=0;
		
		try {
			consulta = con.prepareStatement("DELETE FROM `biblioteca`.`autores`\r\n"
					+ "WHERE idAutor = ?");
			
			consulta.setInt(1, autor.getIdAutor());
			resultado=consulta.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error al realizar el borrado de Autor: "+e.getMessage());
		} finally {
			try {
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return resultado;
    }
*/
}
