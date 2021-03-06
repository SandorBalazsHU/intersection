package hu.elte.project.intersection.controll;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
* <h1>Logger - Az osztály a naplózást végzi a gyári naplókezelő segítségével.</h1>
* Az osztály <b>nem példányosítható</b> csak a writeLog statikus metódusa használható!
* Az osztályból <b>származtatni nem lehet</b><br>Minden adattagja és metódusa <b>statikus</b>.
* <h2><b>Funkciók</b>:</h2>
* <ol>
*   <li><b>Üres</b> log írása</li>
*   <li>Log írása <b>szöveggel</b></li>
*   <li><b>Kivétel</b> naplózása</li>
* </ol>  
* <h2>Az osztályhoz tartozó <b>teszt</b>:</h2>
* hu.elte.project.intersection.controll/<b>LoggerTest.java</b>
* 
* @author  <b>Sándor Balázs</b> - <b>Bognár Dániel</b> - <b>Kiss Erik</b>
* @version 1.0
* @since   2016-05-03 
*/
public final class Logger {
    
    /**
     * A Logger osztály <b>nem példányosítható</b> mivel csak a log-fileba írásra való<br>
     * és minden adattagja és metódusa <b>statikus</b>.
     */
    private Logger(){}
    
    /**
     * A logfile <b>nevét</b> és <b>helyét</b> tartalmazó osztályszintű konstans.
     */
    protected static final String STRING_LOG_FILE_PATH = "intersectionLog.log";
    
    /**
     * java.util.logging.Logger létrehozása a JAVA <b>beépített naplózójának</b> használatához.
     */
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("intersectionLog"); 
    
    /**
     * Osztályszintű változó a <b>fájlkezeléshez</b>.
     */
    private static FileHandler fh;
    
    /**
     * Beépített <b>formázó</b> logokhoz.
     */
    private static final SimpleFormatter formatter = new SimpleFormatter();
    
    /**
     * <b>A napló író metódus</b>
     * Azért van rá szügség, hogy <b>elfedhesük</b> a felhasználó szeme elől a hibákat.
     * Képes <b>többféle</b> naplóbejegyzést készíteni a <b>STRING_LOG_FILE_PATH</b> ban megadott fájlba.
     * Ha a fájl nem létezik akkor <b>létrehozza</b> azt és onnantól abba dolgozik.
     * <b>Nem törli</b> a korábbi naplóbejegyzéseket.
     * 
     * A lehetséges <b>gyári JAVA</b> log szintek:
     * <ol>
     *  <li>SEVERE (highest value)</li>
     *  <li>WARNING</li>
     *  <li>INFO</li>
     *  <li>CONFIG</li>
     *  <li>FINE</li>
     *  <li>FINER</li>
     *  <li>FINEST (lowest value)</li>
     * </ol>
     * 
     * A következő két log szint <b>ülön</b> is kiadható:
     * <ol>
     *  <li>info</li>
     *  <li>warning</li>
     * </ol>
     * 
     * @param type A bejegyzés típusa
     * @param inLog Magát a naplóbejegyzést kell megadni.
     */
    public static void writeLog(String type, String inLog){
        
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.FINEST);
        try {  
            fh = new FileHandler(STRING_LOG_FILE_PATH, true);  
            logger.addHandler(fh);
            fh.setFormatter(formatter);
            switch(type){
                case "warning" :
                   logger.warning(inLog);
                   break;
                case "info" :
                    logger.info(inLog);
                    break;
                default:
                    logger.log(Level.parse(type), inLog);
            }
        } catch (SecurityException | IOException e){
            System.err.println(e);
        } finally {
            try { 
            fh.close();
            } catch(SecurityException e) {
                System.err.println("Hiba a logfile bezárásakor:" + e + "\n" + stackTracePrint(e));
            }
        }
    }
    
    /**
     * Csak egy paraméterrel hívva <b>info</b>-t ír (Rekurzívan a kódismétlés kerülése miatt)
     * 
     * @param inLog Magát a naplóbejegyzést kell megadni.
     */
    public static void writeLog(String inLog){
        Logger.writeLog("info",inLog);
    }
    
    /**
     * <b>Kívétel</b> adható neki amit lelogol <b>warning</b> ként! (Rekurzívan a kódismétlés kerülése miatt)
     * 
     * @param inLog Egy kivételt vár paraméterül amit naplóz.
     */
    public static void writeLog(Exception inLog){
        Logger.writeLog("warning", "\t"+inLog.toString()+"\n\tstackprint: "+stackTracePrint(inLog));
    }
    
    /**
     * Képes <b>szöveggé</b> alakítani egy stackTrace-t.
     * 
     * @param ex A szöveggé alakítandó kivételt várja.
     * @return A megadott kivételt adja vissza szövegesen.
     */
    public static String stackTracePrint(Exception ex){
        String stackTrace = "";
        StackTraceElement[] tmp = ex.getStackTrace();
        for (StackTraceElement e: tmp){
            stackTrace += e.toString()+"\n";
        }
        return stackTrace;
    }
}