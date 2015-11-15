# BungeeBan

<h5>What is BungeeBan?</h5>
<p>BungeeBan is a fully customizable and easy to use BungeeCord plugin which manages your punishments.</p>

<h5>Features</h5>
<p>BungeeBan packs a lot of useful features which makes it superiour to most of the other BungeeCord ban plugins.</p>
<p>
  <ul>
    <li>Easy to use and configure</li>
    <li>BungeeBan stores the bans in MySQL</li>
    <li>Full UUID Support</li>
    <li>Fully configurable broadcasts</li>
    <li>Ban your players on the proxy so you don't need to put this plugin on all of your servers</li>
    <li>Fully configurable messages and prefix</li>
    <li>Report system included</li>
    <li>Kick system included</li>
    <li>Warn system included</li>
    <li>The plugin automatically checks for updates so you are allways up to date</li>
    <li>Custom ban and mute commands which allows the user full customizability of the commands</li>
    <li>The plugin includes a big and documented API which you can use to develop extensions</li>
    <li>Edit the plugin to your likings by editing the source</li>
  </ul>
</p>
<h5>What you need for the plugin</h5>
<ul>
  <li>BungeeCord server</li>
  <li>Stable MySQL Server/Connection with a database and a user with login who has permission to write and read from the database</li>
  <li>BungeeCord permissionssystem like BungeePerms</li>
  <li>Little knowledge about MySQL(phpmyadmin etc.) and knowledge about BungeeCord permissions. (All is explained at the installation page, but if you have problems contact me on <a href="mailto:vinci@vincidev.tk">vinci@vincidev.tk</a>.</li>
</ul>
<h5>Open source &amp; developer friendly API</h5>
  <p>If you are a developer yourself you can edit the plugin or use the API. You can find both in the navbar. How easy the API is is shown here:</p>
  <p><code>UUID uuid = BungeeBan.getUniqueID("Vincii");</code></p>
  <p><code>long end = BungeeBan.getBanEnd(uuid);</code></p>
  <p><code>System.out.println("Vinci is banned untill " + end + "!");</code></p>
