function update(path, componentIdToUpdate) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById(componentIdToUpdate).innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", path, true);
    xhttp.send();
}