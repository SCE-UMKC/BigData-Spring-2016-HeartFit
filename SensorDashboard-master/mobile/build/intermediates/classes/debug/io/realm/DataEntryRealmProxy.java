package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.github.pocmo.sensordashboard.database.DataEntry;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnType;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataEntryRealmProxy extends DataEntry
    implements RealmObjectProxy {

    private static long INDEX_ANDROIDDEVICE;
    private static long INDEX_TIMESTAMP;
    private static long INDEX_X;
    private static long INDEX_Y;
    private static long INDEX_Z;
    private static long INDEX_ACCURACY;
    private static long INDEX_DATASOURCE;
    private static long INDEX_DATATYPE;
    private static Map<String, Long> columnIndices;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("androidDevice");
        fieldNames.add("timestamp");
        fieldNames.add("x");
        fieldNames.add("y");
        fieldNames.add("z");
        fieldNames.add("accuracy");
        fieldNames.add("datasource");
        fieldNames.add("datatype");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    @Override
    public String getAndroidDevice() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(INDEX_ANDROIDDEVICE);
    }

    @Override
    public void setAndroidDevice(String value) {
        realm.checkIfValid();
        row.setString(INDEX_ANDROIDDEVICE, (String) value);
    }

    @Override
    public long getTimestamp() {
        realm.checkIfValid();
        return (long) row.getLong(INDEX_TIMESTAMP);
    }

    @Override
    public void setTimestamp(long value) {
        realm.checkIfValid();
        row.setLong(INDEX_TIMESTAMP, (long) value);
    }

    @Override
    public float getX() {
        realm.checkIfValid();
        return (float) row.getFloat(INDEX_X);
    }

    @Override
    public void setX(float value) {
        realm.checkIfValid();
        row.setFloat(INDEX_X, (float) value);
    }

    @Override
    public float getY() {
        realm.checkIfValid();
        return (float) row.getFloat(INDEX_Y);
    }

    @Override
    public void setY(float value) {
        realm.checkIfValid();
        row.setFloat(INDEX_Y, (float) value);
    }

    @Override
    public float getZ() {
        realm.checkIfValid();
        return (float) row.getFloat(INDEX_Z);
    }

    @Override
    public void setZ(float value) {
        realm.checkIfValid();
        row.setFloat(INDEX_Z, (float) value);
    }

    @Override
    public int getAccuracy() {
        realm.checkIfValid();
        return (int) row.getLong(INDEX_ACCURACY);
    }

    @Override
    public void setAccuracy(int value) {
        realm.checkIfValid();
        row.setLong(INDEX_ACCURACY, (long) value);
    }

    @Override
    public String getDatasource() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(INDEX_DATASOURCE);
    }

    @Override
    public void setDatasource(String value) {
        realm.checkIfValid();
        row.setString(INDEX_DATASOURCE, (String) value);
    }

    @Override
    public long getDatatype() {
        realm.checkIfValid();
        return (long) row.getLong(INDEX_DATATYPE);
    }

    @Override
    public void setDatatype(long value) {
        realm.checkIfValid();
        row.setLong(INDEX_DATATYPE, (long) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_DataEntry")) {
            Table table = transaction.getTable("class_DataEntry");
            table.addColumn(ColumnType.STRING, "androidDevice");
            table.addColumn(ColumnType.INTEGER, "timestamp");
            table.addColumn(ColumnType.FLOAT, "x");
            table.addColumn(ColumnType.FLOAT, "y");
            table.addColumn(ColumnType.FLOAT, "z");
            table.addColumn(ColumnType.INTEGER, "accuracy");
            table.addColumn(ColumnType.STRING, "datasource");
            table.addColumn(ColumnType.INTEGER, "datatype");
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_DataEntry");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_DataEntry")) {
            Table table = transaction.getTable("class_DataEntry");
            if (table.getColumnCount() != 8) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 8 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 8; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            columnIndices = new HashMap<String, Long>();
            for (String fieldName : getFieldNames()) {
                long index = table.getColumnIndex(fieldName);
                if (index == -1) {
                    throw new RealmMigrationNeededException(transaction.getPath(), "Field '" + fieldName + "' not found for type DataEntry");
                }
                columnIndices.put(fieldName, index);
            }
            INDEX_ANDROIDDEVICE = table.getColumnIndex("androidDevice");
            INDEX_TIMESTAMP = table.getColumnIndex("timestamp");
            INDEX_X = table.getColumnIndex("x");
            INDEX_Y = table.getColumnIndex("y");
            INDEX_Z = table.getColumnIndex("z");
            INDEX_ACCURACY = table.getColumnIndex("accuracy");
            INDEX_DATASOURCE = table.getColumnIndex("datasource");
            INDEX_DATATYPE = table.getColumnIndex("datatype");

            if (!columnTypes.containsKey("androidDevice")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'androidDevice'");
            }
            if (columnTypes.get("androidDevice") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'androidDevice'");
            }
            if (!columnTypes.containsKey("timestamp")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'timestamp'");
            }
            if (columnTypes.get("timestamp") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'long' for field 'timestamp'");
            }
            if (!columnTypes.containsKey("x")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'x'");
            }
            if (columnTypes.get("x") != ColumnType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'x'");
            }
            if (!columnTypes.containsKey("y")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'y'");
            }
            if (columnTypes.get("y") != ColumnType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'y'");
            }
            if (!columnTypes.containsKey("z")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'z'");
            }
            if (columnTypes.get("z") != ColumnType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'z'");
            }
            if (!columnTypes.containsKey("accuracy")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'accuracy'");
            }
            if (columnTypes.get("accuracy") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'accuracy'");
            }
            if (!columnTypes.containsKey("datasource")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'datasource'");
            }
            if (columnTypes.get("datasource") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'datasource'");
            }
            if (!columnTypes.containsKey("datatype")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'datatype'");
            }
            if (columnTypes.get("datatype") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'long' for field 'datatype'");
            }
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The DataEntry class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_DataEntry";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    public static Map<String,Long> getColumnIndices() {
        return columnIndices;
    }

    public static DataEntry createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        DataEntry obj = realm.createObject(DataEntry.class);
        if (!json.isNull("androidDevice")) {
            obj.setAndroidDevice((String) json.getString("androidDevice"));
        }
        if (!json.isNull("timestamp")) {
            obj.setTimestamp((long) json.getLong("timestamp"));
        }
        if (!json.isNull("x")) {
            obj.setX((float) json.getDouble("x"));
        }
        if (!json.isNull("y")) {
            obj.setY((float) json.getDouble("y"));
        }
        if (!json.isNull("z")) {
            obj.setZ((float) json.getDouble("z"));
        }
        if (!json.isNull("accuracy")) {
            obj.setAccuracy((int) json.getInt("accuracy"));
        }
        if (!json.isNull("datasource")) {
            obj.setDatasource((String) json.getString("datasource"));
        }
        if (!json.isNull("datatype")) {
            obj.setDatatype((long) json.getLong("datatype"));
        }
        return obj;
    }

    public static DataEntry createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        DataEntry obj = realm.createObject(DataEntry.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("androidDevice") && reader.peek() != JsonToken.NULL) {
                obj.setAndroidDevice((String) reader.nextString());
            } else if (name.equals("timestamp")  && reader.peek() != JsonToken.NULL) {
                obj.setTimestamp((long) reader.nextLong());
            } else if (name.equals("x")  && reader.peek() != JsonToken.NULL) {
                obj.setX((float) reader.nextDouble());
            } else if (name.equals("y")  && reader.peek() != JsonToken.NULL) {
                obj.setY((float) reader.nextDouble());
            } else if (name.equals("z")  && reader.peek() != JsonToken.NULL) {
                obj.setZ((float) reader.nextDouble());
            } else if (name.equals("accuracy")  && reader.peek() != JsonToken.NULL) {
                obj.setAccuracy((int) reader.nextInt());
            } else if (name.equals("datasource")  && reader.peek() != JsonToken.NULL) {
                obj.setDatasource((String) reader.nextString());
            } else if (name.equals("datatype")  && reader.peek() != JsonToken.NULL) {
                obj.setDatatype((long) reader.nextLong());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static DataEntry copyOrUpdate(Realm realm, DataEntry object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static DataEntry copy(Realm realm, DataEntry newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        DataEntry realmObject = realm.createObject(DataEntry.class);
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setAndroidDevice(newObject.getAndroidDevice() != null ? newObject.getAndroidDevice() : "");
        realmObject.setTimestamp(newObject.getTimestamp());
        realmObject.setX(newObject.getX());
        realmObject.setY(newObject.getY());
        realmObject.setZ(newObject.getZ());
        realmObject.setAccuracy(newObject.getAccuracy());
        realmObject.setDatasource(newObject.getDatasource() != null ? newObject.getDatasource() : "");
        realmObject.setDatatype(newObject.getDatatype());
        return realmObject;
    }

    static DataEntry update(Realm realm, DataEntry realmObject, DataEntry newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setAndroidDevice(newObject.getAndroidDevice() != null ? newObject.getAndroidDevice() : "");
        realmObject.setTimestamp(newObject.getTimestamp());
        realmObject.setX(newObject.getX());
        realmObject.setY(newObject.getY());
        realmObject.setZ(newObject.getZ());
        realmObject.setAccuracy(newObject.getAccuracy());
        realmObject.setDatasource(newObject.getDatasource() != null ? newObject.getDatasource() : "");
        realmObject.setDatatype(newObject.getDatatype());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("DataEntry = [");
        stringBuilder.append("{androidDevice:");
        stringBuilder.append(getAndroidDevice());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{timestamp:");
        stringBuilder.append(getTimestamp());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{x:");
        stringBuilder.append(getX());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{y:");
        stringBuilder.append(getY());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{z:");
        stringBuilder.append(getZ());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{accuracy:");
        stringBuilder.append(getAccuracy());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{datasource:");
        stringBuilder.append(getDatasource());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{datatype:");
        stringBuilder.append(getDatatype());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        String realmName = realm.getPath();
        String tableName = row.getTable().getName();
        long rowIndex = row.getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataEntryRealmProxy aDataEntry = (DataEntryRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aDataEntry.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aDataEntry.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aDataEntry.row.getIndex()) return false;

        return true;
    }

}
