const lis = document.getElementsByTagName("li");

for (const li of lis) {
    const temp = li.textContent.substring(0, 5);

    li.addEventListener("click", () => {
        let id = 1;
        if (temp == "Modyf") id = 2;
        else if (temp == "Enkap") id = 3;
        
        changeFrame(id);
        changeBold(id);
    });
}

function changeFrame(id) {
    const frame = document.getElementById("frame")
    const src = frame.src

    const index = src.search(".html");
    const temp = src.substring(0, index-6);
    
    const newSrc = `${temp}frame${+id}.html`;

    frame.src = newSrc;
}

function changeBold(id) {
    removeBold();
    lis[id-1].style.fontWeight = "bold";
}

function removeBold() {
    for (const li of lis) {
        li.style.fontWeight = "normal";
    }
}