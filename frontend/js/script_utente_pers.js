const body = document.querySelector('body'),
        sidebar = body.querySelector('nav'),
        toggle = body.querySelector(".toggle"),
        btnDettagli = document.getElementById('dettagli'),
        btnEventiO = document.getElementById('eventiO'),
        btnEventiPass = document.getElementById('eventiPass'),
        btnEventiFutt = document.getElementById('eventiFutt'),
        btnRecensioni = document.getElementById('recensioni'),
        btnPrefer = document.getElementById('preferiti'),
        iDettagli = document.getElementById('idettagli'),
        spanDettagli = document.getElementById('spandettagli'),
        iEventiO = document.getElementById('ieventiO'),
        spanEventiO = document.getElementById('spaneventiO'),
        iEventiPass = document.getElementById('ieventiPass'),
        spanEventiPass = document.getElementById('spaneventiPass'),
        iEventiFutt = document.getElementById('ieventiFutt'),
        spanEventiFutt = document.getElementById('spaneventiFutt'),
        iRecen = document.getElementById('irecen'),
        spanRecen = document.getElementById('spanrecen'),
        iPrefer = document.getElementById('ipreferiti'),
        spanPrefer = document.getElementById('spanpreferiti');

toggle.addEventListener("click" , () =>{
    sidebar.classList.toggle("close");
})


const PADRE = document.getElementById("padre")
const URL="http://localhost:8080/"

const H2 =document.getElementById("titolo-pagina")
const PUL = document.getElementById("btn-evento")

const ID_UTENTE = localStorage.getItem("id_utente")
let IMGURL = ""

btnDettagli.addEventListener("click", () =>{
    iDettagli.classList.add("active");
    spanDettagli.classList.add("active");
    iEventiO.classList.remove("active");
    spanEventiO.classList.remove("active");
    iEventiPass.classList.remove("active");
    spanEventiPass.classList.remove("active");
    iEventiFutt.classList.remove("active");
    spanEventiFutt.classList.remove("active");
    iRecen.classList.remove("active");
    spanRecen.classList.remove("active");
    iPrefer.classList.remove("active");
    spanPrefer.classList.remove("active");
    PUL.innerHTML = ""
    dettagliUtente()
})
btnEventiO.addEventListener("click", () =>{
    iEventiO.classList.add("active");
    spanEventiO.classList.add("active");
     iEventiPass.classList.remove("active");
    spanEventiPass.classList.remove("active");
    iEventiFutt.classList.remove("active");
    spanEventiFutt.classList.remove("active");
    iDettagli.classList.remove("active");
    spanDettagli.classList.remove("active");
    iRecen.classList.remove("active");
    spanRecen.classList.remove("active");
    iPrefer.classList.remove("active");
    spanPrefer.classList.remove("active");
    const select = document.createElement("select");
    select.id = "select-eventi"
    let option = document.createElement("option");
    option.text = "Tutti";
    option.value = "tutti";
    select.add(option);
    option = document.createElement("option");
    option.text = "Passati";
    option.value = "passati";
    select.add(option);
    option = document.createElement("option");
    option.text = "Futuri";
    option.value = "futuri";
    select.add(option);
    PUL.appendChild(select);
    listaEventiOrg("tutti")
})
btnEventiPass.addEventListener("click", () =>{
     iEventiPass.classList.add("active");
    spanEventiPass.classList.add("active");
    iEventiO.classList.remove("active");
    spanEventiO.classList.remove("active");
    iEventiFutt.classList.remove("active");
    spanEventiFutt.classList.remove("active");
    iDettagli.classList.remove("active");
    spanDettagli.classList.remove("active");
    iRecen.classList.remove("active");
    spanRecen.classList.remove("active");
    iPrefer.classList.remove("active");
    spanPrefer.classList.remove("active");
    PUL.innerHTML = ""
    listaEventiParPass()
})
btnEventiFutt.addEventListener("click", () =>{
    iEventiFutt.classList.add("active");
    spanEventiFutt.classList.add("active");
    iEventiPass.classList.remove("active");
    spanEventiPass.classList.remove("active");
    iEventiO.classList.remove("active");
    spanEventiO.classList.remove("active");
    iDettagli.classList.remove("active");
    spanDettagli.classList.remove("active");
    iRecen.classList.remove("active");
    spanRecen.classList.remove("active");
    iPrefer.classList.remove("active");
    spanPrefer.classList.remove("active");
    PUL.innerHTML = ""
    listaEventiParFutt()
})
btnRecensioni.addEventListener("click", () =>{
    iRecen.classList.add("active");
    spanRecen.classList.add("active");
    iEventiO.classList.remove("active");
    spanEventiO.classList.remove("active");
    iEventiPass.classList.remove("active");
    iEventiFutt.classList.remove("active");
    spanEventiFutt.classList.remove("active");
    spanEventiPass.classList.remove("active");
    iDettagli.classList.remove("active");
    spanDettagli.classList.remove("active");
    iPrefer.classList.remove("active");
    spanPrefer.classList.remove("active");
    PUL.innerHTML = ""
    listaRecensioni()
})
btnPrefer.addEventListener("click", () =>{
    iRecen.classList.remove("active");
    spanRecen.classList.remove("active");
    iEventiO.classList.remove("active");
    spanEventiO.classList.remove("active");
    iEventiPass.classList.remove("active");
    iEventiFutt.classList.remove("active");
    spanEventiFutt.classList.remove("active");
    spanEventiPass.classList.remove("active");
    iDettagli.classList.remove("active");
    spanDettagli.classList.remove("active");
    iPrefer.classList.add("active");
    spanPrefer.classList.add("active");
    PUL.innerHTML = ""
    listaPreferiti()
});

PUL.addEventListener("change", () =>{
    listaEventiOrg(document.getElementById('select-eventi').value)
});

function listaPreferiti(){
    PADRE.innerHTML = ""
    H2.innerHTML = "I tuoi host preferiti"
    fetch(`${URL}preferiti/utente/${ID_UTENTE}`)
        .then(risposta=>risposta.json())
        .then(risposta=>{
            console.log(risposta);
            if(risposta.length == 0){
                PADRE.innerHTML = "<h3 style='text-align: center;'>Non hai host tra i preferiti</h3>"
            }else{
            stampaListaPreferiti(risposta)
            }
        })
}

function dettagliUtente(){
    console.log("Dett")
    PADRE.innerHTML = ""
    H2.innerHTML = "Le tue informazioni"
    fetch(`${URL}users/user/${ID_UTENTE}`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(risposta=>risposta.json())
        .then(risposta=>{
            console.log(risposta);
            stampaDettagliUtente(risposta)
    })
}

function listaEventiParPass(){
    PADRE.innerHTML = ""
    H2.innerHTML = "Eventi a cui hai partecipato"
    fetch(`${URL}partecipation/passate/${ID_UTENTE}`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(risposta=>risposta.json())
        .then(risposta=>{
            if(risposta.length == 0){
                PADRE.innerHTML = "<h3 style='text-align: center;'>Non ci sono eventi</h3>"
            }else{
                console.log(risposta);
                stampaEventiPar(risposta,"passate")
            }
        })
}

function listaEventiParFutt(){
    PADRE.innerHTML = ""
    H2.innerHTML = "Eventi a cui sei iscritto"
    fetch(`${URL}partecipation/future/${ID_UTENTE}`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(risposta=>risposta.json())
        .then(risposta=>{
            if(risposta.length == 0){
                PADRE.innerHTML = "<h3 style='text-align: center;'>Non ci sono eventi</h3>"
            }else{
                console.log(risposta);
                stampaEventiPar(risposta,"future")
            }
        })
}

async function listaEventiOrg(pulsante){
    PADRE.innerHTML = ""
    H2.innerHTML = "Eventi che hai organizzato"
    let futuri = await fetch(`${URL}users/events/futuri/${ID_UTENTE}`)
    futuri = await futuri.json()
    let passati = await fetch(`${URL}users/events/passati/${ID_UTENTE}`)
    passati = await passati.json()
    if(passati.length > 0 && pulsante != "futuri")
        stampaEventiOrg(passati,"passati")
    if(futuri.length > 0 && pulsante != "passati")
        stampaEventiOrg(futuri,"futuri")
    if(futuri.length == 0 && passati.length == 0)
        PADRE.innerHTML = "<h3 style='text-align: center;'>Non hai organizzato eventi</h3>"
    else if(futuri.length == 0 && pulsante == "futuri")
        PADRE.innerHTML = "<h3 style='text-align: center;'>Non hai organizzato eventi futuri</h3>"
    else if(passati.length == 0 && pulsante == "passati")
        PADRE.innerHTML = "<h3 style='text-align: center;'>Non hai organizzato eventi in passato</h3>"
}

function listaRecensioni(){
    H2.innerHTML = "Recensioni che hai creato"
    PADRE.innerHTML = ""
    console.log("re")
    fetch(`${URL}users/reviews/${ID_UTENTE}`)
        .then(risposta=>risposta.json())
        .then(risposta=>{
            if(risposta.length == 0){
                PADRE.innerHTML = "<h3 style='text-align: center;'>Non ci sono recensioni</h3>"
            }else{
                console.log(risposta);
                stampaListaRecensioni(risposta)
            }
        })
}

const INPUT = document.createElement('input');

function readURL() {
    if (INPUT.files && INPUT.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $('#imagePreview').css('background-image', 'url('+e.target.result +')');
            $('#imagePreview').hide();
            $('#imagePreview').fadeIn(650);
        }
        reader.readAsDataURL(INPUT.files[0]);
        console.log(reader)
    }
}

async function stampaDettagliUtente(utente){
    const foto = await fetch(`${URL}images/download/${ID_UTENTE}`)
    const blob = await foto.blob()
    const reader = new FileReader();
    reader.onloadend = function() {
        IMGURL = reader.result;
        
        
        
    // Impostare l'URL dell'immagine come valore dell'attributo src dell'elemento img
    PADRE.innerHTML = `
    <div class="registration-container" style="float:none; margin:auto; width:75%;">
    <div class="form-container">
      <form>
        <div class="container">
          <div class="avatar-upload">
              <div class="avatar-preview">
                  <div id="imagePreview" style="background-image: url(${IMGURL});">
                  </div>
              </div>
          </div>
      </div>
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" placeholder="${utente['nome']}">

        <label for="cognome">Cognome:</label>
        <input type="text" id="cognome" name="cognome" placeholder="${utente['cognome']}">

        <label for="telefono">Telefono:</label>
        <input type="text" id="telefono" name="telefono" placeholder="${utente['telefono']}">

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" placeholder="${utente['email']}">

        <label for="password">Password:</label>
        <input type="password" id="password" name="password">

        <label for="genere">Genere:</label>
        <select id="genere" name="genere" required>
          <option value="0">Seleziona il genere</option>
          <option value="1">Maschio</option>
          <option value="2">Femmina</option>
          <option value="3">Altro</option>
          <option value="4">Non Specificare</option>
        </select>
        <label for="descrizione">Descrizione personale:</label>
        <textarea id="descrizione" name="descrizione" >${utente['presentazione']}</textarea>
        <!--foto profilo-->
        
        <!--
        <label for="foto_profilo">Foto del Profilo:</label>
        <input type="file" id="foto_profilo" name="foto_profilo" required accept="image/*">
        -->
        

        <input type="submit" value="Modifica" onclick="inviaDati()">
      </form>
    </div>
    `;
    }
    reader.readAsDataURL(blob);
}




async function inviaDati(){
    const nome = document.getElementById('nome').value
    const cognome = document.getElementById('cognome').value
    const presentazione = document.getElementById('descrizione').value
    const telefono = document.getElementById('telefono').value
    const gender = document.getElementById('genere').value
    let dati ={};
    if (nome != "")
        dati["nome"] = nome;
    if (cognome!= "")
        dati["cognome"] = cognome;
    if (presentazione!= "")
        dati["presentazione"] = presentazione;
    if (telefono!= "")
        dati["telefono"] = telefono;
    if (gender!= 0)
        dati["gender"] = gender;

    const risposta = await fetch(`${URL}users/update/${ID_UTENTE}`,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dati)
    })
    console.log(risposta)
}

function stampaListaPreferiti(preferiti){
    preferiti.forEach(preferito => {
        console.log(preferito)
        const DIV = document.createElement('div');
        DIV.setAttribute('class','lista-pref');
        fetch(`${URL}images/download/${preferito.utentePreferito.id_utente}`)
        .then(blob => blob.blob())
        .then(blob => {
        const reader = new FileReader();
        reader.onloadend = function() {
            const imgUrl = reader.result;
        let pref = `
        <div class="lista-icona-pref">
            <img src="${imgUrl}">
        </div>
        <div class="lista-dettagli-pref">
            <p class="lista-host-pref">${preferito.utentePreferito.nome} ${preferito.utentePreferito.cognome}</p>
            <p class="lista-testo-pref">Telefono: ${preferito.utentePreferito.telefono}</p>
            <p class="lista-testo-pref">Email: ${preferito.utentePreferito.email}</p>
            <button class="btn-crea-recen" onclick="vediUtente(${preferito.utentePreferito.id_utente})">Vedi utente</button>
            <button class="btn-crea-recen" onclick="rimuoviFollow(${preferito.id})">Rimuovi follow</button>
        </div>
        `;
        DIV.innerHTML = pref;
        PADRE.appendChild(DIV)
        }
        reader.readAsDataURL(blob);
    });
    });
}

function rimuoviFollow(id){
    fetch(`${URL}preferiti/delete/${id}`,{
        method: 'DELETE'
    })
    .then(location.reload())
}


function vediUtente(id_utente){
    localStorage.setItem("id_host", id_utente);
    window.location.href = "schermata_utente.html";
}

function stampaListaRecensioni(recensioni){
    recensioni.forEach(recensione => {
        const DIV = document.createElement('div');
        DIV.setAttribute('class','lista-recen');
        DIV.innerHTML = `
            <div class="lista-icona-recen">
                <img src="../img/${img_categoria(recensione.categoria)}">
            </div>
            <div class="lista-dettagli-recen">  
                <p class="lista-titolo-recen">Evento: ${recensione.evento}</p>
                <p class="lista-testo-recen">Data: ${gestioneData(recensione.data_inizio)}</p>
                <p class="lista-testo-recen">Descrizione: ${recensione.recensione}</p>
                <p class="lista-testo-recen">Voto: ${recensione.voto}</p>
                <button class="btn-crea-recen" onclick="rimuoviRecensione(${recensione.id_recensione})">Rimuovi recensione</button>
            </div>
        `;
        PADRE.appendChild(DIV)

    });
}

function rimuoviRecensione(id_recensione){
    fetch(`${URL}reviews/delete/${id_recensione}`,{
        method: 'DELETE'
    })
    .then(risposta=>risposta.json())
    .then(location.reload())
}

function utenteLogout() {
    localStorage.removeItem('id_utente');
    localStorage.removeItem('token');
    window.location.href = "index.html";
}

function stampaEventiOrg(eventi,tipo){
    console.log("eventi"+eventi)
    eventi.forEach(element => {
        const DIV = document.createElement('div');
        DIV.setAttribute('class','ricerca-evento');
        const pos = gestionePosizione(element.posizione);
        let evento = `
            <div class="ricerca-icona-evento">
                <img src="../img/${img_categoria(element.categoria)}">
            </div>
            <div class="ricerca-dettagli-evento">
                <p class="ricerca-titolo-evento">${element.titolo}</p>
                <p class="ricerca-testo-evento">Data Inizio: ${gestioneData(element.data_inizio)}</p>
                <p class="ricerca-testo-evento">Data fine: ${gestioneData(element.data_fine)}</p>
                <p class="ricerca-testo-evento">Partecipanti: ${element.partecipanti}/${element.n_max_partecipanti}</p>
                <p class="ricerca-testo-evento">Posizione: ${pos[0]}</p>
                <p class="ricerca-testo-evento">${pos[1]}</p>
        `;
        if (tipo == "passati"){
            evento += `
                <p class="ricerca-testo-evento">Recensioni: ${element.media_voti}/10</p>
                
            `;
        }else{
            evento += `
                <button class="btn-crea-recen" onclick="vediEvento(${element.id_evento})">Vedi evento</button>
                <button class="btn-crea-recen" onclick="eliminaEvento(${element.id_evento})">Elimina evento</button>
            `;
        }
        evento += `
            </div>
        `;
        DIV.innerHTML = evento;
        PADRE.appendChild(DIV)
});
}

function stampaEventiPar(eventi,tipo){
    eventi.forEach(element => {
        const DIV = document.createElement('div');
        DIV.setAttribute('class','ricerca-evento');
        const pos = gestionePosizione(element.evento.posizione);
        console.log(element)
        let evento = `
            <div class="ricerca-icona-evento">
                <img src="../img/${img_categoria_num(element.evento.categoria.id_categoria)}">
            </div>
            <div class="ricerca-dettagli-evento">
                <p class="ricerca-titolo-evento">${element.evento.titolo}</p>
                <p class="ricerca-testo-evento">Host: ${element.id_utente.nome} ${element.id_utente.cognome}</p>
                <p class="ricerca-testo-evento">Data Inizio: ${gestioneData(element.evento.data_inizio)}</p>
                <p class="ricerca-testo-evento">Max partecipanti: ${element.evento.n_max_partecipanti}</p>
                <p class="ricerca-testo-evento">Posizione: ${pos[0]}</p>
                <p class="ricerca-testo-evento">${pos[1]}</p>
                ${pulsantePartecipazione(element,tipo)}
            
            
        `;
        DIV.innerHTML = evento;
        PADRE.appendChild(DIV)
});

}

function pulsantePartecipazione(evento,tipo){
    console.log(evento.evento)
    if(tipo == "passate"){
        const div =  `
        <button class="btn-crea-recen" id="btn-open-recen" onclick="apriRecen(${evento.evento.id_evento})">Crea recensione</button>
        </div>
        <div class="bg-modal" id="bg-modal-${evento.evento.id_evento}">
            <div class="modal-contents">
                <div class="btn-close" id="btn-close-recen" onclick="chiudiRecen(${evento.evento.id_evento})">+</div>
                <form>
                    <h2>Lasci la recensione per l'evento:</h2>
                    <h3>${evento.evento.titolo}</h3>
                    <input type="text" class="input-modal" id="rec-descrizione-${evento.evento.id_evento}" placeholder="Descrizione">
                    <input type="range" class="input-modal" min="1" max="10" value="5" class="slider" id="myRange-${evento.evento.id_evento}" oninput="cambiaVoto(${evento.evento.id_evento})">
                    Voto: <span id="myVoto-${evento.evento.id_evento}">5</span>
                    <input type="submit" class="btn-modal" value="Invia" onclick="creaRecensione(${evento.evento.id_evento})">
                </form>

            </div>
        </div>
        `;
        return div;
    }else{
        return `
        <button class="btn-crea-recen" onclick="vediEvento(${evento.evento.id_evento})">Vedi evento</button>
        <button class="btn-crea-recen" onclick="annullaPartecipazione(${evento.evento.id_evento})" width="250px">Annulla Partecipazione</button>
        </div>`;
    }
}

function apriRecen(id_evento) {
	document.getElementById(`bg-modal-${id_evento}`).style.display = "flex";
};

function chiudiRecen(id_evento) {
	document.getElementById(`bg-modal-${id_evento}`).style.display = "none";
};

function cambiaVoto(id_evento){
    let val = document.getElementById(`myRange-${id_evento}`).value;
    document.getElementById(`myVoto-${id_evento}`).innerHTML = val;
}

function vediEvento(id_evento){
    localStorage.setItem('id_evento',id_evento);
    window.location.href = "schermata_evento.html";
}

async function creaRecensione(id_evento){

    const voto = document.getElementById(`myVoto-${id_evento}`).innerHTML;
    const descrizione = document.getElementById(`rec-descrizione-${id_evento}`).value;
    const idUtente = ID_UTENTE;
    const idEvento = id_evento;
    const json = JSON.stringify({voto,descrizione,idUtente,idEvento});
    console.log(json)
    const risultato = await fetch(`${URL}reviews/create`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({voto,descrizione,idUtente,idEvento})
    })
    const ris = await risultato.status
    if(ris != 200){
        chiudiRecen(id_evento);
        await swal({
            title: "Hai già creato una recensione per questo evento",
            icon: "error",
            button: "OK"
        })
    }
    else{
        chiudiRecen(id_evento);
        await swal({
            title: "Recensione creata con successo",
            icon: "success",
            button: "OK"
        })
        location.reload();
    }
}

async function eliminaEvento(id_evento){
    console.log(id_evento)
    let willDelete = await swal({
        title: "Conferma",
        text: "Sei sicuro di voler eliminare questo evento?",
        text: "Una mail verrà mandata ai partecipanti per avvisarli",
        icon: "warning",
        buttons: true,
        dangerMode: true
    })
    if (willDelete) {
        const ris = await fetch(`${URL}event/delete/${id_evento}`,{
            method: 'DELETE'
        });
        console.log(ris)
        if (ris.ok){
            await swal({
                title: "Evento eliminato con successo",
                icon: "success",
                button: "OK"
            })
            location.reload();
        }else
            await swal({
                title: "Errore nella eliminazione dell'evento",
                icon: "error",
                button: "OK"
            })
    };
}

async function annullaPartecipazione(id_evento){
    console.log(id_evento)
    let r = await swal({
        title: "Conferma",
        text: "Sei sicuro di voler annullare la partecipazione?",
        icon: "info",
        buttons: true
    })
    if (r) {
        const idUtente = ID_UTENTE;
        const idEvento = id_evento;
        const ris = await fetch(`${URL}partecipation/getId`,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({idUtente,idEvento})
        });
        console.log(ris)
        const data = await ris.json();
        const id_part = data.id_partecipazione;
        const del = await fetch(`${URL}partecipation/delete/${id_part}`,{
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        console.log(del)
        await swal({
            title: "Evento eliminato con successo",
            icon: "success",
            button: "OK"
        })
        location.reload();
    }
}

function creaParagrafo(testo){
    const PAR = document.createElement('p')
    PAR.setAttribute('class','ricerca-testo-evento');
    PAR.innerHTML = testo;
    return PAR;
}