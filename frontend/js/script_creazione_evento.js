var POS = "";

document.getElementById("textPosizione").addEventListener("change", function(){
    controlloPosizione();
});


async function processData(){
    event.preventDefault()
    if (POS!=""){
        let dati = {
            "id_utente" : localStorage.getItem("id_utente"),
            "titolo" : document.getElementById('Titolo').value,
            "id_categoria" : document.getElementById('Categoria').value,
            "descrizione" : document.getElementById('descrizione').value,
            "data_inizio" : document.getElementById('eventDateTimeInizio').value,
            "data_fine" : document.getElementById('eventDateTimeFine').value,
            "posizione" : POS,
            "n_max_partecipanti" : document.getElementById('Partecipanti').value
        };
    
        var url = "http://localhost:8080/event/create"; // L'indirizzo del server locale
      
        var xhr = new XMLHttpRequest();
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        dati = JSON.stringify(dati)
        console.log(dati)
        
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("JSON inviato con successo!");
            }
        };
        let r = await swal({
            title: "Conferma",
            text: "Confermi di voler creare l'evento: " + document.getElementById('Titolo').value+" ?",
            icon: "info",
            buttons: true
        })
        if (r == true) {
            xhr.send(dati);
            await swal({
                title: "Evento creato con successo!",
                icon: "success",
                button: "OK",
            })
            window.location.replace("index.html")
        }
    }else{
        await swal({
            title: "Attenzione",
            text: "Inserisci la posizione",
            icon: "warning",
            button: "OK",
        })
    }
    
}

async function controlloPosizione() {
    const location = document.getElementById('textPosizione').value;
    // ricerca della posizione limitatata per italia e per un elemento solo
    const response = await fetch(`https://nominatim.openstreetmap.org/search?q=${location}&format=json&bounded=1&viewbox=6.6245,35.2882,18.5208,47.0920&polygon_geojson=1`)
    const data = await response.json()
    
            // dati del json
            const firstResult = data[0];
            const lat = firstResult.lat;
            const lon = firstResult.lon;
            // salvatagio stringa posizione
            let indi = firstResult.display_name;
            // y/n
            
            let r = await swal({
                title: "Conferma",
                text: "Sei sicuro che la posizione che hai inserito sia " + indi +" ?",
                icon: "info",
                buttons: true
            })
            console.log(r)
            if (r == true) {
                //stampa dati
                indi = indi.split(', ');
                let indirizzo = `${indi[1]}, ${indi[0]}, ${indi[4]}, ${indi[5]}, ${indi[7]}`;
                console.log(firstResult);
                POS = JSON.stringify({
                    "lat":lat,
                    "lon":lon,
                    "indirizzo":indirizzo
                });
                console.log(POS)
                document.getElementById("textPosizione").value=indirizzo;
            }
            else {
                document.getElementById("textPosizione").value = "";
            }
        
}