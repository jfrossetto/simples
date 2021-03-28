
//$(document).ready(function() {
//	console.log(" jquery on ");
//});

function abrirModal(id) {
	console.log("abirModal ->" + id);
    var componente = $("#" + id);
    componente.modal('show');
}

function fecharModal(id) {
    var componente = $("#" + id);
    componente.modal('hide');
}
