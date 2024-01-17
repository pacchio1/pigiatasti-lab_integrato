const body = document.querySelector('body'),
        sidebar = body.querySelector('nav'),
        toggle = body.querySelector(".toggle"),
        btnDettagli = document.getElementById('dettagli'),
        btnEventi = document.getElementById('eventi'),
        btnRecensioni = document.getElementById('recensioni'),
        iDettagli = document.getElementById('idettagli'),
        spanDettagli = document.getElementById('spandettagli'),
        iEventi = document.getElementById('ieventi'),
        spanEventi = document.getElementById('spaneventi'),
        iRecen = document.getElementById('irecen'),
        spanRecen = document.getElementById('spanrecen'),
        btnBack = document.getElementById('li-backEvento');
toggle.addEventListener("click" , () =>{
    sidebar.classList.toggle("close");
})



const PADRE = document.getElementById("padre")
const H2 =document.getElementById("titolo-pagina")
const PUL = document.getElementById("btn-evento")

const ID_HOST = localStorage.getItem('id_host')
const ID_UTENTE = localStorage.getItem('id_utente')
const URL=`http://localhost:8080`

PUL.addEventListener("change", () =>{
    listaEventi(document.getElementById('select-eventi').value)
});

btnDettagli.addEventListener("click", () =>{
    iDettagli.classList.add("active");
    spanDettagli.classList.add("active");
    iEventi.classList.remove("active");
    spanEventi.classList.remove("active");
    iRecen.classList.remove("active");
    spanRecen.classList.remove("active");
    PUL.innerHTML = ""
    dettagliUtente()
})
btnEventi.addEventListener("click", () =>{
    iEventi.classList.add("active");
    spanEventi.classList.add("active");
    iDettagli.classList.remove("active");
    spanDettagli.classList.remove("active");
    iRecen.classList.remove("active");
    spanRecen.classList.remove("active");
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
    listaEventi("tutti")
})
btnRecensioni.addEventListener("click", () =>{
    iRecen.classList.add("active");
    spanRecen.classList.add("active");
    iEventi.classList.remove("active");
    spanEventi.classList.remove("active");
    iDettagli.classList.remove("active");
    spanDettagli.classList.remove("active");
    PUL.innerHTML = ""
    listaRecensioni()
})

btnBack.addEventListener("click", () =>{
    event.preventDefault();
    history.back();
});

dettagliUtente()

function dettagliUtente(){
    console.log("Dett")
    PADRE.innerHTML = ""
    fetch(`${URL}/users/user/${ID_HOST}`,{
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(risposta=>risposta.json())
        .then(risposta=>{
            console.log(risposta);
            stampaDettagliUtente(risposta)
            H2.innerHTML = `${risposta.nome} ${risposta.cognome}`
            localStorage.setItem('nome_host', risposta.nome)
            localStorage.setItem('cognome_host', risposta.cognome)
        })
}

async function listaEventi(pulsante){
    PADRE.innerHTML = ""
    H2.innerHTML = "Eventi organizzati da: "+localStorage.getItem('nome_host')+" "+localStorage.getItem('cognome_host')
    let futuri = await fetch(`${URL}/users/events/futuri/${ID_HOST}`)
    futuri = await futuri.json()
    let passati = await fetch(`${URL}/users/events/passati/${ID_HOST}`)
    passati = await passati.json()
    if(passati.length > 0 && pulsante != "futuri")
        stampaListaEventi(passati,"passati")
    if(futuri.length > 0 && pulsante != "passati")
        stampaListaEventi(futuri,"futuri")
    if(futuri.length == 0 && passati.length == 0)
        PADRE.innerHTML = "<h3 style='text-align: center;'>Non ha organizzato eventi</h3>"
    else if(futuri.length == 0 && pulsante == "futuri")
        PADRE.innerHTML = "<h3 style='text-align: center;'>Non ha organizzato eventi futuri</h3>"
    else if(passati.length == 0 && pulsante == "passati")
        PADRE.innerHTML = "<h3 style='text-align: center;'>Non ha organizzato eventi in passato</h3>"
}

function listaRecensioni(){
    H2.innerHTML = `Recensioni create da: ${localStorage.getItem('nome_host')} ${localStorage.getItem('cognome_host')}`
    PADRE.innerHTML = ""
    console.log("re")
    fetch(`${URL}/users/reviews/${ID_HOST}`)
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

function aggiungiUtentePreferito(id_utente,utente_preferito){
    console.log("AAA")
    fetch(`${URL}/preferiti/add`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({id_utente,utente_preferito})
    })
    .then(location.reload())
}

function rimuoviUtentePreferito(id){
    fetch(`${URL}/preferiti/delete/${id}`, {
        method: "DELETE"
    })
    .then(location.reload())
}

async function stampaDettagliUtente(utente){
    const foto = await fetch(`${URL}/images/download/${utente.id_utente}`)
    const blob = await foto.blob()
    const reader = new FileReader();
    reader.onloadend = async function() {
        const imgUrl = reader.result;
        const DIV = document.createElement('div')
        DIV.setAttribute('class','profile-card')

        let temp = document.createElement('div')
        temp.setAttribute('class','image2')
        const IMG = document.createElement('img')
        IMG.src=imgUrl
        IMG.setAttribute('class','profile-pic')
        temp.appendChild(IMG)

        DIV.appendChild(temp)

        temp = document.createElement('div')
        temp.setAttribute('class','data')
        let text = document.createElement('h2')
        text.innerHTML=`${utente['nome']} ${utente['cognome']}`
        temp.appendChild(text)

        temp = document.createElement('div')
        temp.setAttribute('class','row')

        let info = document.createElement('div')
        info.setAttribute('class','info')
        text = document.createElement('h3')
        text.innerHTML="Gender"
        info.appendChild(text)
        text = document.createElement('span')
        text.innerHTML=utente['id_gender']["sesso"]
        info.appendChild(text)
        temp.appendChild(info)

        info = document.createElement('div')
        info.setAttribute('class','info')
        text = document.createElement('h3')
        text.innerHTML="Email"
        info.appendChild(text)
        text = document.createElement('span')
        text.innerHTML=utente['email']
        info.appendChild(text)
        temp.appendChild(info)

        info = document.createElement('div')
        info.setAttribute('class','info')
        text = document.createElement('h3')
        text.innerHTML="Telefono"
        info.appendChild(text)
        text = document.createElement('span')
        text.innerHTML=utente['telefono']
        info.appendChild(text)
        temp.appendChild(info)

        DIV.appendChild(temp)

        temp = document.createElement('div')
        temp.setAttribute('class','data')
        text = document.createElement('h3')
        text.innerHTML="Descrizione"
        temp.appendChild(text)
        text = document.createElement('span')
        text.innerHTML=utente['presentazione']
        temp.appendChild(text)
        DIV.appendChild(temp)

        const id_utente = ID_UTENTE
        const utente_preferito = ID_HOST
        const json = JSON.stringify({id_utente,utente_preferito})
        let pref = await fetch(`${URL}/preferiti/isFollowing`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: json
        })
        try{
            pref = await pref.json()
            temp = `<button class="btn-crea-recen" onclick="rimuoviUtentePreferito(${pref.id})">
            Rimuovi dai preferiti
            </button>`
        }catch(err){
            temp = `<button class="btn-crea-recen" onclick="aggiungiUtentePreferito(${id_utente},${utente_preferito})">Aggiungi ai preferiti</button>`
        }
        console.log(pref)
        DIV.innerHTML+=temp
        PADRE.appendChild(DIV)
    }
    reader.readAsDataURL(blob);
}


function vediEvento(id_evento){
    localStorage.setItem('id_evento',id_evento);
    window.location.href = "schermata_evento.html";
}

function stampaListaEventi(eventi,tipo){
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
            let media_voti = element.media_voti;
            if (media_voti == null)
                media_voti = 0;
            evento += `
                <p class="ricerca-testo-evento">Recensioni: ${media_voti}/10</p>
                
            `;
        }else{
            evento += `
                <button class="btn-crea-recen" onclick="vediEvento(${element.id_evento})">Vedi evento</button>
            `;
        }
        evento += `
            </div>
        `;
        DIV.innerHTML = evento;
        PADRE.appendChild(DIV)
});
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
            </div>
        `;
        PADRE.appendChild(DIV)

    });
}

function creaParagrafo(testo){
    const PAR = document.createElement('p')
    PAR.setAttribute('class','ricerca-testo-evento');
    PAR.innerHTML = testo;
    return PAR;
}