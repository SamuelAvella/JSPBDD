package individuos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import crud.CRUD;
/** IndividuoService
 * Clase que implementa la interfaz CRUD para la tabla Individuo
 * Funciona como un servicio de acceso a datos para el model Individuo
 */
public class IndividuoService implements CRUD<Individuo>, FamilyTree<Individuo>{
    // Conexión activa con la base de datos
    private Connection conn;

    /** IndividuoService (Constructor)
     * Constructor que crea la instancia del servicio y
     * almacena la conexión a la base de datos 
     * @param conn Conexión con la base de datos
     */
    public IndividuoService(Connection conn){
        this.conn = conn;
    }


    private ArrayList<Individuo> _requestAll(String sql) throws SQLException{
        //Creamos el array que vamos a devolver
        ArrayList<Individuo> result = new ArrayList<Individuo>();
        //Construimos la consulta a realizar
        Statement statement = this.conn.createStatement();   

        // Ejectuamos la consulta
        ResultSet querySet = statement.executeQuery(sql);
        // Recorremos cada uno de los registro devueltos por la consulta
        while(querySet.next()) {
            //Obtenemos los datos del Individuo
            int id = querySet.getInt("id");
            String nombre = querySet.getString("nombre");
            String apellido1 = querySet.getString("apellido1");
            String apellido2 = querySet.getString("apellido2");
            int prog1 = querySet.getInt("progenitor1");
            int prog2 = querySet.getInt("progenitor2");
            Date fechaNac = querySet.getDate("fecha_nac");
            Date fechaDef = querySet.getDate("fecha_def");
            //Añadimos el individuo al array que vamos a devolver
            result.add(new Individuo(id, nombre, apellido1, apellido2, prog1, prog2, fechaNac, fechaDef));
        } 
        //Cerramos la consulta
        statement.close();

        //Devolvemos el array de individuoes
        return result;
    }

    @Override
    public ArrayList<Individuo> requestAll(String sortedBy) throws SQLException{
        String sql = "SELECT id, nombre, apellido1, apellido2, progenitor1, progenitor2, fecha_nac, fecha_def FROM individuos"+(sortedBy.length()>0?(" ORDER BY "+sortedBy):"");
        return this._requestAll(sql);
    }

    @Override
    public Individuo requestById(long id) throws SQLException{
        //Varible conteniendo el Individuo a devolver
        Individuo result = null;
        //Construimos la consulta a realizar
        Statement statement = this.conn.createStatement();    
        String sql = String.format("SELECT id, nombre, apellido1, apellido2, progenitor1, progenitor2, fecha_nac, fecha_def FROM individuos WHERE id=%d", id);
        // Ejecución de la consulta
        ResultSet querySet = statement.executeQuery(sql);
        // Recorrido del resultado de la consulta
        if(querySet.next()) {
            //Obtenemos los datos del Individuo
            String nombre = querySet.getString("nombre");
            String apellido1 = querySet.getString("apellido1");
            String apellido2 = querySet.getString("apellido2");
            int prog1 = querySet.getInt("progenitor1");
            int prog2 = querySet.getInt("progenitor2");
            Date fechaNac = querySet.getDate("fecha_nac");
            Date fechaDef = querySet.getDate("fecha_def");
            //creamos el individuo y lo apuntamos a la variable de
            //retorno de este método
            result = new Individuo(id, nombre, apellido1, apellido2, prog1, prog2, fechaNac, fechaDef);
        }
        //Cerramos la consulta
        statement.close();    
        //Devolvemos el individuo
        return result;
    }

    @Override
    public long create(Individuo object) throws SQLException{
        //Obtenemos los datos del Individuo a insertar
        long id = object.getId();
        String nombre = object.getNombre();
        String apellido1 = object.getApellido1();
        String apellido2 = object.getApellido2();
        long prog1 = object.getProgenitor1();
        String sprog1 = (prog1==0)?"NULL":String.format("%d",prog1);
        long prog2 = object.getProgenitor2();
        String sprog2 = (prog2==0)?"NULL":String.format("%d",prog2);
        String fnac = "'"+object.getFechaNac().toString()+"'";
        //Creamos la consulta
        Statement statement = this.conn.createStatement();    
        String sql = String.format("INSERT INTO individuos (apellido1, apellido2, nombre, progenitor1, progenitor2, fecha_nac, fecha_def) VALUES ('%s', '%s', '%s', %s, %s, %s, %s)",nombre, apellido1, apellido2, sprog1, sprog2, fnac, "NULL");
        // Ejecución de la consulta
        int affectedRows = statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            // Devolvemos una excepción si no se ha creado el individuo
            throw new SQLException("Creating user failed, no rows affected.");
        }

        //Aquí llegaremos si se ha creado satisfactoriamente el individuo
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                //Devolvemos el identificador del individuo creado
                id = generatedKeys.getLong(1);
                statement.close();
                return id;
            }
            else {
                //Aquí detectamos que hemos perdido conexión con el servidor de la
                //base de datos y devolvemos una excepción
                statement.close();
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    @Override
    public int update(Individuo object) throws SQLException{
        //Obtenemos los datos del individuo para actualizar
        //en la base de datos
        long id = object.getId();
        String nombre = object.getNombre();
        String apellido1 = object.getApellido1();
        String apellido2 = object.getApellido2();
        long prog1 = object.getProgenitor1();
        String sprog1 = (prog1==0)?"NULL":String.format("%d",prog1);
        long prog2 = object.getProgenitor2();
        String sprog2 = (prog2==0)?"NULL":String.format("%d",prog2);
        String fnac = "'"+object.getFechaNac().toString()+"'";
        String fdef = object.getFechaDef()!=null?"'"+object.getFechaDef().toString()+"'":"NULL";
        //Creamos la consulta
        Statement statement = this.conn.createStatement(); 

        String sql = String.format("UPDATE individuos SET nombre = '%s', apellido1 = '%s', apellido2 = '%s',  progenitor1 = %s, progenitor2 = %s, fecha_nac = %s, fecha_def = %s WHERE id=%d", nombre, apellido1, apellido2, sprog1, sprog2, fnac, fdef, id);
        // Ejecución de la consulta
        int affectedRows = statement.executeUpdate(sql);
        //Cerramos la consulta
        statement.close();
        //Devolvemos excepción si ha habido un error
        //o si no se ha actualizado ningún registro
        //y en caso contrario devolvemos el número de
        //registros que se han actualizado
        if (affectedRows == 0)
            throw new SQLException("Creating user failed, no rows affected.");
        else
            return affectedRows;
    }

    @Override
    public boolean delete(long id) throws SQLException{
        // Creamos la consulta
        Statement statement = this.conn.createStatement();    
        String sql = String.format("DELETE FROM individuos WHERE id=%d", id);
        // Ejecución de la consulta
        int result = statement.executeUpdate(sql);
        // Cerramos la consulta
        statement.close();
        // Devolvemos verdadero si se ha eliminado el individuo
        // o falso en caso contrario
        return result==1;
    }

    @Override
    public ArrayList<Individuo> query(String column, String value) throws SQLException {
        String sql =String.format("SELECT id, nombre, apellido1, apellido2, progenitor1, progenitor2, fecha_nac, fecha_def FROM individuos WHERE %s='%s'", column, value);
        return this._requestAll(sql);
    }

    @Override
    public ArrayList<Individuo> query(String column, long value) throws SQLException {
        String sql =String.format("SELECT id, nombre, apellido1, apellido2, progenitor1, progenitor2, fecha_nac, fecha_def FROM individuos WHERE %s=%d", column, value);
        return this._requestAll(sql);
    }

    private void obtainAncestry(Individuo individuo, ArrayList<Individuo> ancestry){
        try {
            if(individuo.getProgenitor1()!=0){
                Individuo prog1 = this.requestById(individuo.getProgenitor1());
                ancestry.add(prog1);
                obtainAncestry(prog1, ancestry);
            }
            if(individuo.getProgenitor2()!=0){
                Individuo prog2 = this.requestById(individuo.getProgenitor2());
                ancestry.add(prog2);
                obtainAncestry(prog2, ancestry);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void obtainOffspring(Individuo individuo, ArrayList<Individuo> offspring){
        try {
            for(Individuo i: this.query("progenitor1", individuo.getId())){
                offspring.add(i);
                obtainOffspring(i, offspring);
            }
            for(Individuo i: this.query("progenitor2", individuo.getId())){
                offspring.add(i);
                obtainOffspring(i, offspring);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public ArrayList<Individuo> queryAncestry(long id) throws SQLException{
        Individuo individuo = null;
        if((individuo = this.requestById(id))!=null){
            ArrayList<Individuo> ancestry = new ArrayList<Individuo>();
            this.obtainAncestry(individuo, ancestry);
            return ancestry;
        }    
        return new ArrayList<Individuo>();
    }

    @Override
    public ArrayList<Individuo> queryOffspring(long id) throws SQLException{
        Individuo individuo = null;
        if((individuo = this.requestById(id))!=null){
            ArrayList<Individuo> offspring = new ArrayList<Individuo>();
            obtainOffspring(individuo, offspring);
            return offspring;
        }    
        return new ArrayList<Individuo>();
    }

}
