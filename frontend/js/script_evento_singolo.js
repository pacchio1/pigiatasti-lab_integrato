//const URL = "./test/singolo_evento.json"
const URL = `http://localhost:8080/`
const PADRE = document.getElementById("padre");
let TEL = ""
let EMAIL = ""
let mostra = false;

function backRicercaEventi(){
    event.preventDefault();
    window.location.href = "ricerca_eventi.html";
}

console.log(localStorage.getItem('id_evento'))

fetch(`${URL}event/${localStorage.getItem('id_evento')}`,{
    method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
})
    .then (risposta => risposta = risposta.json())
    .then (risposta => risposta[0])
    .then (risposta => {
        localStorage.setItem('id_host',risposta['id_utente'])
        console.log(risposta)
        //titolo
        let DIV = document.createElement('div');
        DIV.setAttribute('class',"singolo-titolo-evento");

        let testo = `
            <button class="singolo-btnBack" onclick="backRicercaEventi()">&#8592;</button>
            <p class="singolo-titolo">${risposta['titolo']}</p>
            <img src="../img/${img_categoria(risposta['categoria'])}" class="singolo-icona-titolo" />
        `;
        DIV.innerHTML = testo;

        PADRE.appendChild(DIV);

        DIV = document.createElement('div');
        DIV.setAttribute('class','singolo-dettagli-evento');

        let data_inizio = gestioneData(risposta['data_inizio']);
        let data_fine = gestioneData(risposta['data_fine']);
        let posizione = gestionePosizione(risposta['posizione']);
        testo = `
            <div class="singolo-dettagli" onclick="mostraUtente(${risposta['id_utente']})">
                <img class="singolo-icona" src="../../img/icona_utente.png" />
                <p class="singolo-testo" style="font-size: large;">${risposta['nome_host']}</p>
                <p class="singolo-testo" style="font-size: large;">${risposta['cognome_host']}</p>
            </div>
            <div class="singolo-dettagli">
                <img class="singolo-icona" src="../../img/icona_calendario.png" />
                <p class="singolo-testo" style="font-size: large;">${data_inizio}</p>
                <p class="singolo-testo" style="font-size: large;">${data_fine}</p>
            </div>
            <div class="singolo-dettagli" onclick="window.open('https://www.google.com/maps?q=${posizione[2]},${posizione[3]}&z=15')">
                <img class="singolo-icona" src="../../img/icona_posizione.png" />
                <p class="singolo-testo" style="font-size: large;">${posizione[0]}</p>
                <p class="singolo-testo" style="font-size: large;">${posizione[1]}</p>
            </div>
        `;
        DIV.innerHTML = testo;
        PADRE.appendChild(DIV);


        DIV = document.createElement('div');
        DIV.setAttribute('class','singolo-contenuto');
        testo = `
            <div class="singolo-descrizione">
                <p style="font-size:xx-large;">Descrizione: </p>
                <p style="font-size:x-large;">${risposta['descrizione']}</p>
            </div>
            <div class="singolo-numero-partecipanti">
                <p><span class="singolo-titolo-span">Numero di partecipanti: </span> ${risposta["partecipanti"]}/${risposta["n_max_partecipanti"]}</p>
            </div>
            <div class="singolo-pulsanti">
                <input type="button" class="singolo-btnIscrizione" value="Iscriviti all'evento" onclick="iscrizioneEvento()" />
                <input type="button" class="singolo-btnContatti" value="Contatta l'host" onclick="contattiHost()" />
                <input type="button" class="singolo-btnIscrizione" id="btnMostraPartecipanti" value="Mostra partecipanti" onclick="mostraPartecipanti()" />
            </div>
            <div class="mostraPartecipanti" id="mostraPartecipanti" style="display: none;"></div>
        `;
        DIV.innerHTML = testo;
        PADRE.appendChild(DIV);

        DIV = document.createElement('div');
        DIV.setAttribute('class','singolo-immagine-sinistra');
        testo = `
            <img src="../../img/quadrato_gruppo1.jpg" />
        `;
        DIV.innerHTML = testo;
        PADRE.appendChild(DIV);

    })

async function contattiHost(){
    const risposta = await fetch(`${URL}users/user/${localStorage.getItem('id_host')}`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    const dati = await risposta.json();
    console.log(dati)
    swal({
        title: `Contatta l'host: ${dati.nome} ${dati.cognome}`,
        text: `Telefono: ${dati.telefono} 
        Email: ${dati.email}`,
        icon: "info",
        button: "OK"
    })

}

async function iscrizioneEvento(){
    let r = await swal({
        title: "Conferma",
        text: "Sei sicuro di voler partecipare a questo evento?",
        icon: "info",
        buttons: true
    })
    if (r){
        if(localStorage.getItem('id_utente') == localStorage.getItem('id_host')){
            swal({
                title: "Non ti puoi iscrivere ad un tuo evento!",
                icon: "warning",
                button: "OK"
            })
        }else{
            let idUtente = parseInt(localStorage.getItem('id_utente'))
            let idEvento = parseInt(localStorage.getItem('id_evento'))
            console.log(JSON.stringify({idUtente,idEvento}))
            const risp = await fetch(`${URL}partecipation/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({idUtente,idEvento})
            })
            const risposta = risp.status
            if(risposta == 200)
                await swal({
                    title: "Ti sei iscritto all'evento!",
                    icon: "success",
                    button: "OK"
                })
            else if(risposta == 400)
                await swal({
                    title: "Sei già iscritto all'evento!",
                    icon: "error",
                    button: "OK"
                })
            else
                await swal({
                    title: "Errore durante l'iscrizione!",
                    icon: "error",
                    button: "OK"
                })
            location.reload()
        }
    }
    
}

function mostraUtente(id_utente){
    localStorage.setItem("id_host",id_utente);
    window.location.href ='schermata_utente.html'
}

async function mostraPartecipanti(){
    const DIV = document.getElementById('mostraPartecipanti')
    const BTN = document.getElementById('btnMostraPartecipanti')
    if (mostra){
        DIV.style.display = "none"
        BTN.value = "Mostra partecipanti";
    }else{
        BTN.value = "Nascondi partecipanti";
        const risultato = await fetch(`${URL}event/partecipanti/${localStorage.getItem('id_evento')}`,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        const utenti = await risultato.json();
        DIV.style.display = "block"
        DIV.innerHTML = ""
        let temp = "";
        utenti.forEach(utente => {
            temp = stampaUtente(DIV, utente);
        });
        console.log(temp)
        
    }
    mostra =!mostra
}

async function stampaUtente(DIV,utente){
    const foto = await fetch(`${URL}images/download/${utente.id_utente}`)
    const blob = await foto.blob()
    const reader = new FileReader();
    reader.onloadend = function() {
        const imgUrl = reader.result;
        const temp = `
                    <div class = "lista-utente" onclick="mostraUtente(${utente.id_utente})">
                        <div class = "lista-utente-icona">
                            <img src = "${imgUrl}">
                        </div>
                        <div class = "lista-utente-dettagli">
                            <p class = "lista-utente-nome">${utente.nome}</p>
                            <p class = "lista-utente-nome">${utente.cognome}</p>
                        </div>
                    </div>
                `;
        DIV.innerHTML += temp;
    }
    reader.readAsDataURL(blob);
}