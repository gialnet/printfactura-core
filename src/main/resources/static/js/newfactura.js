 

/**
 * enviar los datos necesario para crear una nueva factura
 * estos datos estan almacenados el la pagina Web newFactura.jsp
 * y se manipulan a través del objeto DOM de la misma
 * oTabla contiene una tabla con las líneas de detalle de la factura
 * xNombre de tipo input text tiene el nombre del cliente
 * input type="date" id="xFecha" la fecha en formato ISO yyyy-mm-dd
 * por lo tanto compondremos un objeto JSON al que le vamos a pasar los datos
 * 
 * @param {type} newfactura
 * @returns {Boolean}
 */
function SendDatosNewFactura(newfactura)
{  

    var obj = {};
    var rejilla = {};
    var myarray = [];

    var Fecha=document.getElementById("xFecha").value;
    var myfila=window.fila;
    
    //alert('test');
    var datalist_id = document.getElementById("lista_nombres");
    var input_id = document.getElementById('xNombre');
    
    //alert('antes del for');
    //idfact
    /*
     * En caso de una modificación el campo xIDFactura será mayor de 0
     */

     if (document.getElementById('xIDFactura').value > 0)
         obj.idfact = document.getElementById('xIDFactura').value;

     //if (document.getElementById('xIDCliente').value > 0)
         obj.id_cliente = document.getElementById('xIDCliente').value;
    /* else
         {
            for (var i=0;i<datalist_id.options.length;i++)
              if (datalist_id.options[i].value === input_id.value) 
                  {   
                      obj.id_cliente = datalist_id.options[i].label;
                      break;
                  }

         }*/
    //alert(obj.id);
    if (typeof obj.id_cliente === "undefined")
        {
            alert("Tiene que seleccionar un cliente");
            return false;
        }
    
    obj.fecha = Fecha;

    
    //alert(myfila);
    
    for (j=1; j<myfila; j++)
        {
            mycel = document.getElementById('oConcepto'+j);
            rejilla.concepto = mycel.innerHTML;
            
            mycel = document.getElementById('oUnidades'+j);
            rejilla.unidades = mycel.innerHTML;
            
            mycel = document.getElementById('oIVA'+j);
            rejilla.por_vat = mycel.innerHTML;
            
            mycel = document.getElementById('oPrecio'+j);
            rejilla.importe = mycel.innerHTML;
        
            myarray.push(rejilla);
            rejilla = {};

            //alert(mycel.innerHTML);
        }
       
    obj.LineasFact=myarray;
    
    var myJsonString = JSON.stringify(obj);
    
    var hiddenField = document.createElement("input");
    hiddenField.setAttribute("type", "hidden");
    hiddenField.setAttribute("name", "myJson");
    hiddenField.setAttribute("value", myJsonString);

    var formulario = document.getElementById(newfactura);
    formulario.appendChild(hiddenField);
    //alert(myJsonString);
    
    newfactura.submit();

}

/**
 * Crea las filas necesarias para mostrar las lineas de detalle de la factura
 * @param {json} myJson
 * @returns {undefined}
 */
function PutLineasDetalle(myJson)
{
    //xIdJSON
    
    //alert(myJson);
    var tabla = new grid("oTabla");
    var j = 0;
    var myfila=window.fila;

    var obj = JSON.parse(myJson);

    //alert(obj.length);
    for (j = 0; j <= (obj.length - 1); j++)
    {
        //alert(obj[j].Descripcion);
        var row = tabla.AddRowTable(j + 1);

        tabla.AddRowCellText(row, 0, (j+1));
        
        var celda = tabla.AddRowCellText(row, 1, obj[j].concepto);
        celda.setAttribute('id', 'oConcepto'+myfila);
        //celda.setAttribute('contentEditable', true);
        celda.setAttribute('onclick', 'GetCell(this.id);');
        
        celda = tabla.AddRowCellNumber(row, 2, obj[j].unidades);
        celda.setAttribute('id', 'oUnidades'+myfila);
        //celda.setAttribute('contentEditable', true);
        celda.setAttribute('onclick', 'GetCell(this.id);');
        
        celda = tabla.AddRowCellNumber(row, 3, obj[j].por_vat);
        celda.setAttribute('id', 'oIVA'+myfila);
        //celda.setAttribute('contentEditable', true);
        celda.setAttribute('onclick', 'GetCell(this.id);');
        
        celda = tabla.AddRowCellNumber(row, 4, obj[j].importe);
        celda.setAttribute('id', 'oPrecio'+myfila);
        //celda.setAttribute('contentEditable', true);
        celda.setAttribute('onclick', 'GetCell(this.id);');
        
        celda = tabla.AddRowCellNumber(row, 5, obj[j].total);
        celda.setAttribute('id', 'oTotal'+myfila);
        //celda.setAttribute('contentEditable', true);
        
        tabla.AddRowCellText(row, 6,
        '<div onclick="DeleteTupla('+(j+1)+');"><a href="#" class="btn tip" title="Ver Documento"> <i class="icon-trash"> </i> </a></div>');

        window.fila++;
        myfila=window.fila;
    }
    
    obj=null;

    
}


/**
 * Añadir una celda a la tabla de nueva factura o modificación
 * @returns {undefined}
 */
function addRowNewBlankFact()
{
 var tabla = new grid("oTabla");
 var myfila = window.fila;
 var myVAT = "21";

//alert(obj.length);
    
        //alert(obj[j].Descripcion);
        var row = tabla.AddRowTable(myfila);

        tabla.AddRowCellText(row, 0, (myfila).toString()); // id
        
        var celda = tabla.AddRowCellText(row, 1, ""); // concepto
        celda.setAttribute('id', 'oConcepto'+myfila);
        //celda.setAttribute('contentEditable', true);
        celda.setAttribute('onclick', 'GetCell(this.id);');
        
        celda = tabla.AddRowCellNumber(row, 2, "1"); // unidades
        celda.setAttribute('id', 'oUnidades'+myfila);
        //celda.setAttribute('contentEditable', true);
        celda.setAttribute('onclick', 'GetCell(this.id);');
        
        if (document.getElementById("xTipoCliente").value==="3")
            myVAT = "0";
        if (document.getElementById("xTipoCliente").value==="4")
            myVAT = "0";
        if (document.getElementById("xTipoCliente").value==="5")
            myVAT = "0";
            
        celda = tabla.AddRowCellNumber(row, 3, myVAT); // % IVA
        celda.setAttribute('id', 'oIVA'+myfila);
        celda.setAttribute('onclick', 'GetCell(this.id);');
        //celda.setAttribute('contentEditable', true);
        
        celda = tabla.AddRowCellNumber(row, 4, "0"); // precio
        celda.setAttribute('id', 'oPrecio'+myfila);
        celda.setAttribute('onclick', 'GetCell(this.id);');
        //celda.setAttribute('contentEditable', true);
        
        celda = tabla.AddRowCellNumber(row, 5, "Total"); // total
        celda.setAttribute('id', 'oTotal'+myfila);
        //celda.setAttribute('contentEditable', true);
        
        tabla.AddRowCellText(row, 6,
                '<a onclick="deleteRowDetalle('+myfila+')" class="btn tip" title="Eliminar Concepto"> <i class="icon-trash"> </i> </a>');
                
        // variable global definida en el formulario
        // javascript las guarda en el objeto window
        window.fila++;
}

/**
 * Cuando cambia el valor de los escrito lo buscamos en la base de datos
 * @param {type} Nombre
 * @returns {undefined}
 */
function BuscarDatosDB(Nombre)
{
   
    CallRemote('AjaxServlet.servlet', 'accion=LeerDatosClientes&cuantos=lista_nombres&xNombre=' + Nombre);
    // devuelve los datos a LeerDatosClientes(idLista,myJson)
}

/**
 * Ventana desplegable para buscar por nombre
 * @returns {undefined}
 */
function seleccionarCliente()
{
    //alert($("#listaClientes option:selected").val());
    document.getElementById("xIDCliente").value = $("#listaClientes option:selected").val();
    //id_customers_type $('option:selected', this).attr('mytag');
    document.getElementById("xTipoCliente").value = $("#listaClientes option:selected").attr('id_customers_type');

    //alert( document.getElementById("xTipoCliente").value);
     if (document.getElementById("xTipoCliente").value===2)
        document.getElementById("xREQ").innerHTML='--Se le aplicará el recargo de equivalencia--';
    else
        document.getElementById("xREQ").innerHTML='';

}

/**
 * Llenar una lista de opciones con nombres
 * @param {type} myJson
 * @returns {undefined}
 */
function LeerDatosClientes(myJson)
{
    
    var obj = JSON.parse(myJson);
        

     for (j = 0; j <= (obj.length - 1); j++)
    {
        
        $('#listaClientes').append($('<option>', { 
            value: obj[j].ID,
            text : obj[j].Nombre,
            id_customers_type : obj[j].id_customers_type
    }));
    }
    
    var xCliente = document.getElementById('xIDCliente').value;
    if( xCliente.length>0 )
        setClienteIndex(xCliente);
    
}


/**
 * Poner el indice de un select de clientes para las modificaciones de facturas
 * @param {type} xCliente
 * @returns {undefined}
 */
function setClienteIndex(xCliente)
{

    $('#listaClientes').select2('val',xCliente);

}

//
// invocar el servlet que muestra el PDF
//
function VerPDF(numFila)
{
    var xID='ofila'+numFila;
    var oCelda = document.getElementById(xID).cells[0];
    
    window.location.href = 'pdfServlet.servlet?xID='+oCelda.innerHTML;
}
