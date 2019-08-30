# Bibliotheque de la ville 
### Projet de site pour les usagers.
<p>Le but est de permettre aux usagers de suivre leurs prêts en cours, de les prolonger.
<p>Les usagers pourront également consulter/rechercher les livres de la bibliothèque et en connaitre la disponibilité.

#### Architecture de l'application
<p>Le projet est découpé en microservices:
<ul>
    <li>config-server: appelle un repo git avec les configurations des serveurs</li>
    <li>eureka-server: lie les services avec eureka</li>
    <li>zuul-server: API Gateway</li>
    <li>microservice-books: services contient les livres et emprunts, BDD libraryMbooks</li>
    <li>microservice-users: services contient les utilisateurs et roles, BDD libraryMusers</li>
    <li>microservice-mailings: services contient les mails envoyés et l'automatisation des envois, BDD libraryMmailings</li>
    <li>microservice-clientui: services web servant à l'affichage des données du site</li>
</ul>

### Deploiement

<ol>
 <li>Installer le JDK d'Oracle 1.8 </li>
 <li>Cloner le répertoire</li>
   <li>Installer Maven version minimum 2.</li>
   <li>Installer Tomcat 9.0.14.</li>
   <li>Créez un repository github pour le dossier bibliotheque-config-server-repo</li>
   <li>Dans le service config-server, modifiez src\main\resources\application.properties: 
   spring.cloud.config.server.git.uri= l'adresse de votre repository</li>
   <li>Créer trois de données Postgresql:
           <ul>
               <li>Une pour le microservice-books </li>
               <li>Une pour le microservice-users </li>
               <li>Une pour le microservice-mailing </li>
           </ul>
      </li>
   <li>Dans votre repo bibliotheque-config-server-repo,  modifier pour les trois microservices les informations suivantes:
   <ul>
      <li>le nom de votre base de données: jdbc.url = jdbc:postgresql://localhost:5432/"nom-de-la-base-de-données" </li>
      <li>votre username: jdbc.username = "username" </li>
      <li>votre mot de passe: jdbc.password = "password" </li>
   </ul>
   </li>
 <li>Faites de même pour les champs flyway:
  <ul>
      <li>le nom de votre de données: flyway.url = jdbc:postgresql://localhost:5432/"nom-de-la-base-de-données" </li>
      <li>votre username: flyway.user = "username" </li>
      <li>votre mot de passe: flyway.password = "password" </li>
   </ul>
 </li> 
 <li>Dans le microservice-users, modifiez src\main\resources\db\migration\V1.2__init_data.sql afin de créér des utilisateurs</li>
 <li>Lancez les microservices:
  <ul>
      <li>config-server</li>
      <li>eureka-server</li>
      <li>zuul-server</li>
      <li>microservice-books</li>
      <li>microservice-users</li>
      <li>microservice-mailing</li>
      <li>microservice-clientui</li>
   </ul>
 </li>
 <li>Dans un navigateur rendez vous sur http://localhost:8080/</li>
 <li>Vous pouvez vous connecter avec le pseudo et mot de passe que vous avez créé dans le init_data.sql du service users</li>
 



