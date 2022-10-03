// La función se llama cuando se hace click en el canvas
function clickMe(id) {
	var info;
	var oParam = {
		"ID": id
	};
	$.ajax({
		type: "post",
		url: "php/starsInfoAPI.php",
		data: oParam,
		success: function(response) {
			const res = JSON.parse(response);
			console.table(res);
			info = res[0].info
			//Muestra una alerta
			Swal.fire({
				title: res[0].nombre,
				text: res[0].info,
				confirmButtonText: "Close",
			});
		}
	});
}

function parseSVG(s) {
	var div = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
	div.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg">' + s + '</svg>';
	var frag = document.createDocumentFragment();
	while (div.firstChild.firstChild)
		frag.appendChild(div.firstChild.firstChild);
	return frag;
}

function printStar(x, y, size, id) {

	document.getElementById('panel').appendChild(parseSVG(
		"<circle cx='" + x + "' cy='" + y + "' r='" + size + "' fill='url(#rad1)'  onclick=\"clickMe('" + id + "');\" />"
	));
}

function resize() {
	const box = document.getElementById('map');
	box.style.width = window.innerWidth +"px";
	box.style.height = window.innerHeight-172 +"px";
}