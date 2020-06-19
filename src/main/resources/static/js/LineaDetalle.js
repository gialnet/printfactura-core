/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



//
// Poner un campo input dentro de la celda
//

function GetCell(myID)
{
    //alert(myID);
    var oValor=document.getElementById(myID);
    var stringID=myID.toString();
    var campo;
    
    //alert(stringID);
    
    if (myID.toString().substring(0, 9) ==='oConcepto')
        {
            if (oValor.innerHTML.toString().substring(0,6) ==='<input')
                oValor.innerHTML=document.getElementById('concepto').value;
            else
                {
                    campo='<input name="concepto" onblur="InputToRowCellHtml(this.id,'+stringID+');" value="'+
                            oValor.innerHTML+'" type="text" id="concepto" size="60" maxlength="90" onkeypress="detectar_tecla(event, this.id,'+
                            stringID+')" />';
                    oValor.innerHTML=campo;
                }
            
            document.getElementById('concepto').focus();
        }
        
    if (myID.toString().substring(0, 9)==='oUnidades')
        {
            if (oValor.innerHTML.toString().substring(0,6) ==='<input')
                oValor.innerHTML=document.getElementById('Unidades').value;
            else
                {
                    campo='<input name="Unidades" onblur="InputToRowCellHtml(this.id,'+stringID+');" value="'+
                            oValor.innerHTML+'" type="text" id="Unidades" size="10" maxlength="10" onkeypress="detectar_tecla(event, this.id,'+
                            stringID+')" />';
                    oValor.innerHTML=campo;
                }
                
            document.getElementById('Unidades').focus();
        }

     // IVA
    if (myID.toString().substring(0, 4)==='oIVA')
        {
            //alert('pasa por oIVA');
            if (oValor.innerHTML.toString().substring(0,6) ==='<input')
                oValor.innerHTML=document.getElementById('IVA').value;
            else
                {
                    /*
                     <select name="IVA">
                    <option value="21">21%</option>
                    <option value="10">10%</option>
                    <option value="4">4%</option>
                    <option value="0">0%</option>
                    </select>
                     */
                 campo='<select name="IVA" id="IVA" onblur="InputToRowCellHtml(this.id,'+stringID+');" onkeypress="detectar_tecla(event, this.id,'+
                            stringID+')" ><option value="21">21%</option><option value="10">10%</option><option value="4">4%</option><option value="0">0%</option></select>';
                 /*
                    campo='<input name="Unidades" onblur="InputToRowCellHtml(this.id,'+stringID+');" value="'+
                            oValor.innerHTML+'" type="text" id="Unidades" size="10" maxlength="10" onkeypress="detectar_tecla(event, this.id,'+
                            stringID+')" />'; */
                    oValor.innerHTML=campo;
                    //alert(oValor.innerHTML);
                }
                
            document.getElementById('IVA').focus();
        }
        
    if (myID.toString().substring(0, 7)==='oPrecio')
        {
            //alert('paso');
            if (oValor.innerHTML.toString().substring(0,6) ==='<input')
                oValor.innerHTML=document.getElementById('precio').value;
            else
                {
                    campo='<input name="precio" onblur="InputToRowCellHtml(this.id,'+stringID+');" value="'+
                            oValor.innerHTML+'" type="text" id="precio" size="10" maxlength="10" onkeypress="detectar_tecla(event, this.id,'+
                            stringID+')" />';
                    oValor.innerHTML=campo;
                }
                
            document.getElementById('precio').focus();
        }
    
}



//
// pulsación de las teclas, cuando se pulsa return se graba el valor
//
function detectar_tecla(e, MyinputID, MyrowID){

if (window.event)
	tecla=e.keyCode;
else
	tecla=e.which;

//escape
if(tecla===27){
	
	if (window.event)
	{
		e.returnValue=false;
		window.event.cancelBubble = true;
	}
	else
	{
		e.preventDefault();
		e.stopPropagation();
	}
	// refrescar la pantalla
        InputToRowCellHtml(MyinputID,MyrowID);
	
	
}

if(tecla===13 || tecla===9)
{
	if (window.event)
	{
		e.returnValue=false;
		window.event.cancelBubble = true;
	}
	else
	{
		e.preventDefault();
		e.stopPropagation();
	}

        //alert(MyrowID.toString());
	// Grabar el importe introducido al pulsar return
	InputToRowCellHtml(MyinputID,MyrowID);
	
		
}

//alert(tecla);
}

//
// Poner el contenido VALUE de INPUT TEXT en el innerHTML de una celda de la tabla
//
function InputToRowCellHtml(MyinputID,MyrowID)
{
    var InputValor=document.getElementById(MyinputID).value;
    
    //alert(MyinputID);
    //alert('el id de la fila'+MyrowID.getAttribute('id'));
    MyrowID=MyrowID.getAttribute('id');
    
    if (MyinputID==='concepto')
        {
            var oCelda=document.getElementById(MyrowID.toString());
            
            
            // pasar el foco al campo importe
            var myString=MyrowID.toString();
            var numFila=myString.substring(9);
            //alert(numFila);
            var oPrecio=document.getElementById('oPrecio'+numFila);
            var pID = oPrecio.getAttribute('id');

            if (oCelda.innerHTML.toString().substring(0,6) ==='<input')
                oCelda.innerHTML=InputValor;
            
            campo='<input name="precio" onblur="InputToRowCellHtml(this.id,'+pID+');" value="'+
            oPrecio.innerHTML+'" type="text" id="precio" size="10" maxlength="10" onkeypress="detectar_tecla(event, this.id,'+
            pID+')" />';

            oPrecio.innerHTML=campo;

            
            //oPrecio.focus();
            document.getElementById('precio').focus();
            
        }
        
    if (MyinputID==='Unidades')
        {
            var oCelda=document.getElementById(MyrowID.toString());
            oCelda.innerHTML=InputValor;
            
            
            var myString=MyrowID.toString();
            //alert(myString);
            var numFila=myString.substring(9);
            //alert('fila: '+numFila);
            
            var importe=parseFloat(InputValor);
            
            var oCeldaTotal=document.getElementById('oTotal'+numFila);
            var oPrecio=document.getElementById('oPrecio'+numFila);
            var String_precio = oPrecio.innerHTML;
            var precio=parseFloat(String_precio);
            var unidades=parseFloat(InputValor);
            //alert(unidades);
            var resultado = precio * unidades;
            
            resultado = resultado.toFixed(2);
            //alert(resultado);
            oCeldaTotal.innerHTML= resultado.toString();
            
        }
        
        // IVA
     if (MyinputID==='IVA')
        {
            var oCelda=document.getElementById(MyrowID.toString());
            
            oCelda.innerHTML=InputValor;
            
            
            var myString=MyrowID.toString();
            //alert(myString);            oIVA
            var numFila=myString.substring(4);
            //alert('fila: '+numFila);
            
            var importe=parseFloat(InputValor);
            
            var oCeldaIVA=document.getElementById('oIVA'+numFila).value;
            
            //alert(resultado);
            oCeldaIVA.innerHTML= oCeldaIVA;
            
        }
        
    if (MyinputID==='precio')
        {
            var oCelda=document.getElementById(MyrowID.toString());
            oCelda.innerHTML=InputValor;
            
            //oPrecio
            var myString=MyrowID.toString();
            //alert(myString);
            var numFila=myString.substring(7);
            //alert('fila: '+numFila);
            
            var oCeldaTotal=document.getElementById('oTotal'+numFila);
            var oCeldaUnidades=document.getElementById('oUnidades'+numFila);
            
            
            var importe=parseFloat(InputValor);
            //alert(importe);
            var vUnidades = oCeldaUnidades.innerHTML;
            //alert(oUnidades);
            var unidades=parseFloat(vUnidades);
            //alert(unidades);
            var resultado = importe * unidades;
            
            resultado = resultado.toFixed(2);
            //alert(resultado);
            oCeldaTotal.innerHTML= resultado.toString();
            
            
        }
    
}

/**
 * Borrar una línea de detalle
 * @param {type} myfila
 * @returns {undefined}
 */
function deleteRowDetalle(myfila)
{   
 
    var tbl = document.getElementById('oTabla');
    
    tbl.deleteRow(myfila);
    
    window.fila--;
    
}
