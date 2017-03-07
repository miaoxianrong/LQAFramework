
function $(elementId)
{ 
	if (document.getElementById) { 
	   return document.getElementById(elementId); 
	}else if(document.all) { 
	   return document.all[elementId]; 
	}else if(document.layers) { 
	   return document.layers[elementId]; 
	}
}
function addRow(table)
{
   var sampleRow = table.rows[table.rows.length - 1];
   var sampleCell = sampleRow.getElementsByTagName("td");
   var row = table.rows.length;
   var newRow = document.createElement("tr");
  newRow.style.backgroundColor = "f8fdff";

   for(i = 0; i < sampleCell.length; i ++ )
   {
      var newCell = document.createElement("td");
      newCell.style.width = sampleCell[i].clientWidth+"px";
      newCell.innerHTML=sampleCell[i].innerHTML.replace(new RegExp(eval(danju.lines.value), "g"), eval(danju.lines.value) + 1);
      newCell.childNodes.item(0).name = "cell" + row + i;
      newRow.appendChild(newCell);
   }
   sampleRow.parentNode.appendChild(newRow);
}
function deleteRow(table)
{
   if(table.rows.length == 2)
   window.alert("不允许删除最后一行数据！");
   else
   table.deleteRow(table.rows.length - 1);
}

function pop_show(ele)
{
	eval(ele + ".style.display = ''");
}
  
function pop_hidden(ele)
{
  eval(ele + ".style.display = 'none'");
}

function pop_close()
{
	pop_hidden("popDIV");
	pop_hidden("mbDIV");
}
  
function pop_win(theURL,title)
{
 	popDIV.style.top = "10px";
 	popDIV.style.left = "100px";
 	popDIV.style.width = "460px";
 	popDIV.style.height = "400px";
 	popTitle.innerHTML = title;
 	pop_show("popDIV");
 	pop_show("mbDIV")
	divcontent.innerHTML = "<iframe frameborder=0 scrolling=yes src='"+theURL+"' width='100%' height='100%'></iframe>";
}

function openW(theURL) { 
 	popDIV.style.top = "10px";
 	popDIV.style.left = "100px";
 	popDIV.style.width = "460px";
 	popDIV.style.height = "400px";
 	popTitle.innerHTML = "弹出窗口";
 	pop_show("popDIV");
 	pop_show("mbDIV")
	divcontent.innerHTML = "<iframe frameborder=0 scrolling=yes src='"+theURL+"' width='100%' height='100%'></iframe>";
}