# Seminararbeit
Dieses Projekt ist im Zuge meiner Seminararbeit im Abitur entstanden. Es handelt sich um eine Software, welche ein gewünschtes Verzeichnis nach doppelten Dateien absucht und sie auf Wunsch löscht.





## Wie funktioniert es?

1. Nach dem Starten des Programms wird der Nutzer auf der Konsole gebeten, den Pfad zum zu durchsuchenden Verzeichnis anzugeben.
2. Der Inhalt jeder Datei wird per SHA-256 gehasht und zusammen mit weiteren Informationen über die Datei in einer Instanz der Klasse DataAVLTreeInsert gespeichert.
3. Diese Instanzen werden nach und nach in einem AVL-Baum eingefügt. Als Schlüssel wird hierfür der gehashte Inhalt der jeweiligen Dateien verwendet.
4. Wird beim Einfügen ein Duplikat erkannt (zwei gleiche Schlüssel), wird diese Datei in einer separaten Hashmap gespeichert. Als "Original" gilt immer die älteste Datei.
5. Sind alle Dateien im AVL-Baum bzw. in der Hashmap gespeichert werden alle gefundenen Duplikate auf der Konsole ausgegeben, und der Nutzer kann entscheiden, ob er diese löschen möchte.



