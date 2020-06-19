
var pagina=1;
var pagsize=10;

/*
 
 Clase rejilla para javascript en html 5
 
 */

function grid(idTabla) {

    this.tbl = document.getElementById(idTabla);

    // añadir una fila a un objeto tabla
    this.AddRowTable = function(nFila) {

        var row = this.tbl.insertRow(this.tbl.rows.length);
        row.setAttribute('id', 'ofila' + nFila);
        //row.setAttribute('onclick', 'GetFila(this.id);');
        row.setAttribute('onMouseOver', 'FilaActiva(this.id);');
        row.setAttribute('onMouseOut', 'FilaDesactivar(this.id);');
        
      

        return row;

    };

    /*
     si a valorCell le pasamos un html como este le pasamos una imagen:
     '<img title="ver documento" alt="ver documento" src="img/search.png">'
     */

    // la primera celda es la 0 la segunda la 1 etc.
    this.AddRowCellText = function(row, NumeroCell, ValorCell) {

        // ID 
        var cellText = row.insertCell(NumeroCell); // 0
        var textNode = document.createTextNode(this.tbl.rows.length - 1);
        cellText.appendChild(textNode);
        cellText.innerHTML = ValorCell;

        return cellText;
    };

    // la primera celda es la 0 la segunda la 1 etc.
    this.AddRowCellNumber = function(row, NumeroCell, ValorCell) {

        // ID 
        var cellText = row.insertCell(NumeroCell); // 0
        var textNode = document.createTextNode(this.tbl.rows.length - 1);
        cellText.appendChild(textNode);
        cellText.setAttribute('align', 'right');
        cellText.innerHTML = ValorCell;
        
        return cellText;

    };
    
    this.AddRowCellSelect = function(row, NumeroCell, myFila,tabla) {

        var sel = document.createElement("Select");
        sel.setAttribute('onchange', 'GetCellSelectTipo(this,'+row+','+myFila+','+tabla+')');
        var op1 = new Option('Fijo','Fijo');
        var op2 = new Option('Variable','Variable');
        
        sel.options.add(op1);
        sel.options.add(op2);


        // ID 
        var cellText = row.insertCell(NumeroCell); // 0
        var textNode = document.createTextNode(this.tbl.rows.length - 1);
        
        cellText.appendChild(sel);
        //cellText.setAttribute('align', 'right');
        //.innerHTML = ValorCell;
        
        return cellText;

    };
    
    
   

}
        

//
// Poner el color de fila seleccionada a la fila activa
//
function FilaActiva(xID)
{
    document.getElementById(xID).style.backgroundColor = "#ECF3F5";
}


//
// cambiar el color de una fila selecciona a otra no seleccionada
//
function FilaDesactivar(xID)
{
    document.getElementById(xID).style.backgroundColor = "#FFFFFF";
}


//
// Cojer el valor de la fila que contiene el ID
//
function GetFila(xID)
{
    var oCelda = document.getElementById(xID).cells[0];
    //var oDoc=document.getElementById(xID).cells[6];
    //var fila=document.getElementById(xID).rowIndex;

    //alert(oDoc.innerHTML);

    //if (oDoc.innerHTML!='') 
    //   location="SeguiExpeDocs.php?xIDExpe="+oCelda.innerHTML;

    //location="Notarios.php?xIDNotario="+oCelda.innerHTML;
    alert(oCelda.innerHTML);


}







/**
 * 
 * @param {type} accion
 * @returns {undefined}
 */

function NextPage(accion)
{
    
    window.pagina++;
    var pag=window.pagina;
    var tama=window.pagsize;
    var direccion=accion+'&pagina='+pag +'&size='+tama;
    
    document.getElementById("xPag").innerHTML=window.pagina;
    //alert(direccion);
    CallRemote('AjaxServlet.servlet', direccion);
}

/**
 * 
 * @param {type} accion
 * @returns {undefined}
 */

function PrevPage(accion)
{
    window.pagina--;
    if (window.pagina <1)
        window.pagina=1;
    
    var pag=window.pagina;
    var tama=window.pagsize;
    var direccion=accion+'&pagina='+pag +'&size='+tama;
    document.getElementById("xPag").innerHTML=window.pagina;
    //alert(direccion);
    CallRemote('AjaxServlet.servlet', direccion);
}

/**
 * Borrar las columnas de una tabla
 * @param {type} tblName
 * @returns {undefined}
 */
function deleteLastRow(tblName)
{
var tbl = document.getElementById(tblName);
//if (tbl.rows.length > 1) tbl.deleteRow(tbl.rows.length - 1);
for(x=(tbl.rows.length-1); x>0; x--)
    tbl.deleteRow(x);
}
