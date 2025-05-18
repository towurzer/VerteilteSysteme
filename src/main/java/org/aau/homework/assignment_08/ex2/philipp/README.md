# Analyse der Torrent Datei

Ausgelesen mit Torrent File Editor (https://torrent-file-editor.github.io/)

* Tracker-URL: https://torrent.ubuntu.com/announce
* SHA-1 Hash: 4315e319f87eb6548dece2e58bb006a220ad287a
* Zieldateigröße: 6278520832 Bytes
* Stückgröße: 262144 Bytes
* Anzahl Stücke: 23952

# Tracker Interaktion

## cURL Aufruf
```
curl "https://torrent.ubuntu.com/announce?info_hash=%43%15%e3%19%f8%7e%b6%54%8d%ec%e2%e5%8b%b0%06%a2%20%ad%28%7a&peer_id=-GE0001-abcdefgxyz12&port=6881&uploaded=0&downloaded=0&left=6278520832&event=started" > tracker_response_ubuntu.bencode
```

# Peer Liste
```
{
  'complete': 32,  # Anzahl der bekannten Seeder (Peers mit vollständiger Datei)
  'incomplete': 1, # Anzahl der bekannten Leecher (Peers, die noch herunterladen)
  'interval': 1800, # Empfohlenes Intervall (in Sekunden) bis zur nächsten Anfrage an den Tracker
  'peers': [       # Liste der Peers, mit denen sich der Client verbinden kann
  
    # Details zum ersten Peer
    {              
      'ip': '185.125.190.59',  # Die IP-Adresse des Peers (kann IPv4 oder IPv6 sein)
      'peer id': 'T03I--01M.5UhmP5jk1L', # Eindeutige ID des Peers (vom Client generiert)
      'port': 6900           # Der Port, auf dem der Peer auf eingehende Verbindungen wartet
    },
    
    # Details zum zweiten Peer
    {            
      'ip': '2a02:247a:210:a800::1', # Die IP-Adresse des Peers (hier eine IPv6-Adresse)
      'peer id': '-lt0D80-[Nicht-Text-Bytes]', # Eindeutige ID des Peers (kann nicht-Text-Bytes enthalten)
      'port': 6962           # Der Port des Peers
    }
  ]
}
```
