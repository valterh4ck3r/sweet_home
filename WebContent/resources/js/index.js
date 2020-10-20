
var array = [];
var stop = '';
var erro = false;

function mascara(id) {
    
    var campo = document.getElementById(id);        
    campo.value = campo.value.replace( /[^\d]/g, '' );	                                         
    if ( campo.value.length > 3 ) campo.value = stop;
    else stop = campo.value;    
}

function mascaraValor(id) {
    
    var campo = document.getElementById(id);         
    campo.value = campo.value.replace( /[^\d]/g, '' )
                             .replace(/([0-9]{2})$/g, ".$1");                                               
                            
    if ( campo.value.length > 9 ) campo.value = stop;
    else stop = campo.value;        
    if(campo.value.charAt(0) == '.' ) campo.value = campo.value.replace(".", ""); 
    document.getElementById(id).value = campo.value;
}

function mascaraNumero(id, num) {
    
    var campo = document.getElementById(id);        
    campo.value = campo.value.replace( /[^\d]/g, '' );	                                         
    if ( campo.value.length > parseInt(num) ) campo.value = stop;
    else stop = campo.value;    
}

function mascaraFone(id) {
    
	var fone = document.getElementById(id);        	
    fone.value = fone.value.replace( /[^\d]/g, '' ).replace( /^0/,'' )
                            .replace( /^(\d\d)(\d)/, '($1)$2' )
                            .replace(fone.value.length > 13 ? /(\d{5})(\d)/ : /(\d{4})(\d)/, '$1-$2' );
    if ( fone.value.length > 14 ) fone.value = stop;
    else stop = fone.value;    
}

function mascaraCEP(id) {

    var campo = document.getElementById(id);        
    campo.value = campo.value.replace( /[^\d]/g, '' )            
                            .replace( /(\d{5})(\d)/,"$1-$2" );
    if ( campo.value.length > 9 ) campo.value = stop;
    else stop = campo.value;     
    
    if(campo.value.length < 9) {
    	
    	preencheCampos("");
    	erro = false;
    }
    
    CEP(campo.value);
}


function CEP(cep) {
		
	if(cep == null || cep.length < 9) return;	
		
	cep = cep.replace("-", ""); 
		
	var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var obj = JSON.parse(this.responseText);    
                    	
            	preencheCampos("");
            
            	if(obj.erro && !erro) {
            		alert("O CEP informado não existe!");            	            		
            		erro = true;
            		return;
            	}
            	
            	if(obj.erro) return;
            	                 
            	preencheCampos(JSON.stringify(obj));            
        }        
    };
            xhttp.open("GET", `https://viacep.com.br/ws/${cep}/json/`);
            xhttp.send();
}


const preencheCampos = (x) => {
	
	let obj = x.length === 0 ? "" : JSON.parse(x);
	document.getElementById('form:estado').value = x.length === 0 ? "" : obj.uf;
    document.getElementById('form:cidade').value = x.length === 0 ? "" : obj.localidade;
    document.getElementById('form:bairro').value = x.length === 0 ? "" : obj.bairro;
    document.getElementById('form:rua').value = x.length === 0 ? "" : obj.logradouro;
    document.getElementById('form:numero').value = "";
}


function cidades(estado) {
			
	document.getElementById('form:cidades').value = "";    
	document.getElementById('form:loader').style.display = "block";    
	if(estado == null || estado.length === 0) return;
	
	var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    	
    	document.getElementById('form:loader').style.display = "block";
        if (this.readyState == 4 && this.status == 200) {
            var obj = JSON.parse(this.responseText);    
    
            document.getElementById('form:loader').style.display = "block";
            array = [];
            obj.map((x) => {            	
            	array.push(x.nome);
            });
                 
            document.getElementById('form:cidades').value = array.toString();                                    
            document.getElementById('form:loader').style.display = "none";
        }        
    };
            xhttp.open("GET", `https://servicodados.ibge.gov.br/api/v1/localidades/estados/${estado}/municipios`);
            xhttp.send();
}

