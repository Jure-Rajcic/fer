setup:
    jrajcic@HRZG1MC065 derby-skripte % ./ij-console.sh 
    ij> run 'napravi-blog-bazu.sql';       
        connect 'jdbc:derby://localhost:1527/blogBaza;user=sa;password=sapwd22;create=true'; 
        CALL SYSCS_UTIL.SYSCS_CREATE_USER('blogDBAdmin', 'blogDBPassword');
        CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.fullAccessUsers', 'sa,blogDBAdmin');
        disconnect;
    connect 'jdbc:derby://localhost:1527/blogBaza;user=blogDBAdmin;password=blogDBPassword'; // preporucam da se ne disconnect-as da mozes provjeravati tablice tokom rada 
 
popunimo tablicu:
    u src/main/resources/META-INF/persistence.xml postavi 
        <property name="hibernate.hbm2ddl.auto" value="create" /> 
    zatim pokreni src/main/java/hr/fer/zemris/java/tecaj_13/console/Example1.java kako bi popunio novokreirane tablice

pokreni server:
    u src/main/resources/META-INF/persistence.xml postavi 
        <property name="hibernate.hbm2ddl.auto" value="update" /> 
    mvn clean jetty:run
    http://localhost:8080/blog/servleti/main
    ode http://localhost:8080/blog/servleti/author/ mozes nac sva ieman autora, svaki auutor ima sifru koja je jednaka njegovom nicknameu -> autor jure ima sifru jure, ako bi se zelia ulogirat kao on
