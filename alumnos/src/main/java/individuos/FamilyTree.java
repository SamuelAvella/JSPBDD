package individuos;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FamilyTree<T> {
    public ArrayList<T> queryAncestry(long id) throws SQLException;
    public ArrayList<T> queryOffspring(long id) throws SQLException;
}
