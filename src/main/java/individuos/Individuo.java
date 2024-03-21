package individuos;

import java.sql.Date;

/** Individuo
 * Mantiene los datos de un individuo
 * id: Identificado único del individuo coincide con su PK
 * nombre: Nombre del individuo
 * apellido1: Apellido1 del individuo
 * apellido2: Apellido2 del individuo
 * progenitor1: Id del progenitor1 del individuo
 * progenitor2: Id del progenitor2 del individuo
 */
public class Individuo {

    //Definimos los atributos de la clase
    //de forma privada utilizando Getter y Setters
    //para el acceso a los mismos
    private long id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private long progenitor1;
    private long progenitor2;
    private Date fechaNac;
    private Date fechaDef;

    /** individuo (Contructor)
     * 
     * Construye la instancia con los valores dados
     * 
     * @param id Identificador del individuo
     * @param nombre Nombre del individuo 
     * @param apellido1 Apellido1 del individuo
     * @param apellido2 Apellido1 del individuo
     * @param progenitor1 Id del Progenitor1 del indiviudo
     * @param progenitor2 Id del Progenitor2 del individuo
     */
    public Individuo(long id, String nombre, String apellido1, String apellido2, long progenitor1, long progenitor2, Date fechaNac, Date fechaDef){
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.progenitor1 = progenitor1;
        this.progenitor2 = progenitor2;
        this.fechaNac = fechaNac;
        this.fechaDef = fechaDef;
    }

    /** getId
     * Devuelve el id de un individuo
     * 
     * @return id del individuo
     */
    public long getId() {
        return id;
    }

    /** setId
     * Almacena el id de un individuo
     * @param id id del individuo
     */
    public void setId(long id) {
        this.id = id;
    }

    /** getNombre
     * Devuelve el nombre del individuo
     * @return nombre del individuo
     */
    public String getNombre() {
        return nombre;
    }

    /** setNombre
     * Cambia el nombre del individuo
     * @param nombre nombre del individuo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** getApellido1
     * Devuelve el primer apellido del individuo
     * @return primer apellido del individuo
     */
    public String getApellido1() {
        return apellido1;
    }

    /** setApellido1
     * cambia el primer apellido del individuo
     * @param apellido apellido del individuo
     */
    public void setApellido1(String apellido) {
        this.apellido1 = apellido;
    }

    /** getApellido2
     * Devuelve el segundo apellido del individuo
     * @return segundo apellido del individuo
     */
    public String getApellido2() {
        return apellido2;
    }

    /** setApellido1
     * cambia el segundo apellido del individuo
     * @param apellido apellido del individuo
     */
    public void setApellido2(String apellido) {
        this.apellido2 = apellido;
    }
    
    /** getProgenitor1
     * Devuelve el identificador del progenitor1 del individuo
     * @return identificador del progenitor1
     */
    public long getProgenitor1() {
        return progenitor1;
    }

    /** setProgenitor1
     * Cambia el progenitor 1 del individuo
     * @param progenitor1 id del progenitor 1
     */
    public void setProgenitor1(long progenitor1) {
        this.progenitor1 = progenitor1;
    }

    /** getProgenitor2
     * Devuelve el identificador del progenitor2 del individuo
     * @return identificador del progenitor2
     */
    public long getProgenitor2() {
        return progenitor2;
    }

    /** setProgenitor2
     * Cambia el progenitor 2 del individuo
     * @param progenitor1 id del progenitor 2
     */
    public void setProgenitor2(long progenitor2) {
        this.progenitor2 = progenitor2;
    }

    /** getFechaNac
     * Devuelve la fecha de nacimiento del individuo
     * @return Fecha de nacimiento del individuo
     */
    public Date getFechaNac() {
        return fechaNac;
    }

    /** setFechaNac
     * Cambia la fecha de nacimiento del individuo
     * @param fechaNac Fecha de nacimiento
     */
    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    /** getFechaDef
     * Devuelve la fecha de defunción del individuo
     * @return Fecha de defunción del individuo
     */
    public Date getFechaDef() {
        return fechaDef;
    }

    /** setFechaDef
     * Cambia la fecha de defunción del individuo
     * @param fechaNac Fecha de defunción
     */
    public void setFechaDef(Date fechaDef) {
        this.fechaDef = fechaDef;
    }


    /** equals
     * Compara dos individuos segón su id
     * @param obj individuo con el que comparar
     * @return Devuelve true si el objeto es igual al individuo, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
       return obj!=null && 
            obj.getClass() == this.getClass() && 
            ((Individuo)obj).getId() == this.id;
    }

    /** toString
     * 
     * @return Devuelve una cadena que identifica al individuo
     */
    @Override
    public String toString() {
        return String.format(
            "%3d: %10s %10s, %20s %3d %3d %s %s", 
            id, apellido1, apellido2, nombre, progenitor1, progenitor2, fechaNac==null?"---":fechaNac, fechaDef==null?"---":fechaDef);
    }
}
