/*
 FusionCharts JavaScript Library
 Copyright FusionCharts Technologies LLP
 License Information at <http://www.fusioncharts.com/license>

 @author FusionCharts Technologies LLP
 @version fusioncharts/3.2.2-servicerelease1.4200
*/
(function(){var da=FusionCharts(["private","modules.renderer.highcharts-charts"]);if(da!==void 0){var p=da.hcLib,v=p.BLANKSTRING,fa=p.createTrendLine,l=p.pluck,P=p.getValidValue,o=p.pluckNumber,F=p.defaultPaletteOptions,R=p.getFirstValue,xa=p.getDefinedColor,S=p.parseUnsafeString,D=p.FC_CONFIG_STRING,pa=p.extend2,qa=p.getDashStyle,ya=p.toPrecision,da=p.stubFN,ga=p.hasSVG,za=p.graphics.getColumnColor,C=p.getFirstColor,ha=p.setLineHeight,ra=p.pluckFontSize,ba=p.getFirstAlpha,sa=p.graphics.getDarkColor,
ia=p.graphics.getLightColor,Q=p.graphics.convertColor,ja=p.COLOR_TRANSPARENT,ka=p.POSITION_CENTER,Aa=p.POSITION_TOP,ea=p.POSITION_BOTTOM,Ba=p.POSITION_RIGHT,Ca=p.POSITION_LEFT,d=p.chartAPI,Da=p.titleSpaceManager,Ea=p.placeLegendBlockBottom,Fa=p.placeLegendBlockRight,ta=p.graphics.mapSymbolName,ua=d.singleseries,t=p.COMMASTRING,va=p.STRINGUNDEFINED,T=p.ZEROSTRING,la=p.ONESTRING,L=p.HUNDREDSTRING,ma=p.PXSTRING,Ga=p.COMMASPACE;d("column2d",{standaloneInit:!0,creditLabel:!1},d.column2dbase);d("column3d",
{defaultSeriesType:"column3d",defaultPlotShadow:1},d.column2d);d("bar2d",{isBar:!0,defaultSeriesType:"bar",spaceManager:d.barbase},d.column2d);d("line",{standaloneInit:!0,creditLabel:!1},d.linebase);d("area2d",{standaloneInit:!0,creditLabel:!1},d.area2dbase);d("pie2d",{standaloneInit:!0,defaultSeriesType:"pie",defaultPlotShadow:1,point:function(b,e,c,a,m){var g,f,i,k,h,d,n,$=0,w=0,q=[],r=l(a.plotborderthickness,la),j=o(a.use3dlighting,1)?o(a.radius3d,a["3dradius"],90):100;i=o(a.showzeropies,1);var s=
!0,p=o(a.showpercentintooltip,1),G=o(a.showlabels,1),A=o(a.showvalues,1),u=o(a.showpercentvalues,a.showpercentagevalues,0),H=l(a.tooltipsepchar,a.hovercapsepchar,Ga),x=l(a.labelsepchar,H),I=l(a.plotbordercolor,a.piebordercolor),B=m[D].numberFormatter;k=c.length;j>100&&(j=100);j<0&&(j=0);if(o(a.showlegend,0))m.legend.enabled=!0,m.legend.reversed=!Boolean(o(a.reverselegend,0)),e.showInLegend=!0;if(!G&&!A)m.plotOptions.series.dataLabels.enabled=!1,m.tooltip.enabled===!1&&(s=!1);for(b=0;b<k;b+=1)f=c[b],
g=B.getCleanValue(f.value,!0),g===null||!i&&g===0||(q.push(f),$+=g);e.enableRotation=q.length>1?l(a.enablerotation,1):0;for(b=q.length-1;b>=0;b-=1)if(f=q[b],g=B.getCleanValue(f.value,!0),c=S(l(f.label,f.name,v)),k=l(f.color,m.colors[b%m.colors.length]),h=l(f.alpha,a.plotfillalpha,L),d=l(f.bordercolor,I,ia(k,25)).split(t)[0],n=a.showplotborder==T?T:l(f.borderalpha,a.plotborderalpha,a.pieborderalpha,"80"),i={opacity:Math.max(h,n)/100},e.data.push({showInLegend:c!==v,y:g,name:c,shadow:i,toolText:S(P(f.tooltext)),
color:this.getPointColor(k,h,j),borderColor:Q(d,n),borderWidth:r,link:P(f.link),sliced:Boolean(o(f.issliced,0))}),s)k=B.percentValue(g/$*100),h=B.dataLabels(g)||v,i=p===1?k:h,g=G===1?c:v,k=A===1?u===1?k:h:v,k=(f=P(f.displayvalue))?f:k!==v&&g!==v?g+x+k:l(g,k),c=c!=v?c+H+i:i,f=e.data[w],f.displayValue=k,f.toolText=l(f.toolText,c),w+=1;m.legend.enabled=a.showlegend==la?!0:!1;m.chart.startingAngle=l(a.startingangle,0);return e},getPointColor:function(b,e,c){var a,m,b=C(b),e=ba(e);c<100&&ga?(a=Math.floor((100-
0.35*c)*85)/100,a=sa(b,a),m=Math.floor((100+c)*50)/100,b=ia(b,m),e={FCcolor:{color:b+t+a,alpha:e+t+e,ratio:c+",100",radialGradient:!0}}):e={FCcolor:{color:b+t+b,alpha:e+t+e,ratio:"0,100"}};return e},configureAxis:function(b){var e=0,c=b[D],a;b.plotOptions.series.dataLabels.style=b.xAxis.labels.style;b.plotOptions.series.dataLabels.color=b.xAxis.labels.style.color;delete c.x;delete c[0];delete c[1];b.chart.plotBorderColor=b.chart.plotBackgroundColor=ja;c=c.pieDATALabels=[];if(b.series.length===1&&
(a=b.series[0].data)&&(e=b.series[0].data.length)>0&&b.plotOptions.series.dataLabels.enabled)for(;e--;)a[e]&&P(a[e].displayValue)!==void 0&&c.push(a[e].displayValue)},spaceManager:function(b,e,c,a){var m=b[D],g=m.FCchartName,f=m.smartLabel,i=o(m.pieDATALabels&&m.pieDATALabels.length,0),k=0,h=e.chart;o(h.managelabeloverflow,0);var d=o(h.slicingdistance,20),n=o(h.pieradius,0),$=o(h.enablesmartlabels,h.enablesmartlabel,1);o(h.skipoverlaplabels,h.skipoverlaplabel,1);var w=o(h.issmartlineslanted,1),q=
o(h.labeldistance,h.nametbdistance,5),r=o(h.smartlabelclearance,5);c-=b.chart.marginRight+b.chart.marginLeft;var j=a-(b.chart.marginTop+b.chart.marginBottom),a=Math.min(j,c),s=l(h.smartlinecolor,F.plotFillColor[b.chart.paletteIndex]),p=o(h.smartlinealpha,100),v=o(h.smartlinethickness,1),A=b.plotOptions.series.dataLabels,a=n===0?a*0.15:n,u=0,u=2*a;A.connectorWidth=v;A.connectorColor=Q(s,p);j-=Da(b,e,c,u<j?j-u:j/2);h.showlegend==la&&(l(h.legendposition,ea).toLowerCase()!=Ba?j-=Ea(b,e,c,j/2,!0):c-=Fa(b,
e,c/3,j,!0));for(f.setStyle(A.style);i--;)e=f.getOriSize(m.pieDATALabels[i]),k=Math.max(k,e.width);$&&(q=r+d);n===0&&(u=Math.min(c/2-k,j/2)-q,u>=a?a=u:q=Math.max(q-(a-u),d));b.plotOptions.pie.slicedOffset=d;b.plotOptions.pie.size=2*a;b.plotOptions.series.dataLabels.distance=q;b.plotOptions.series.dataLabels.isSmartLineSlanted=w;b.plotOptions.series.dataLabels.enableSmartLabels=$;if(g==="doughnut2d"||g==="doughnut3d")if(m=o(h.doughnutradius,0),i=o(h.use3dlighting,1)?o(h.radius3d,h["3dradius"],50):
100,i>100&&(i=100),i<0&&(i=0),h=m===0||m>=a?a/2:m,b.plotOptions.pie.innerSize=2*h,i>0&&ga&&(h=parseInt(h/a*100,10),m=(100-h)/2,i=parseInt(m*i/100,10),h=h+t+i+t+2*(m-i)+t+i,b.series[0]&&b.series[0].data)){g=b.series[0].data;b=0;for(i=g.length;b<i;b+=1)if(m=g[b],m.color.FCcolor)m.color.FCcolor.ratio=h}},creditLabel:!1,eiMethods:{togglePieSlice:function(b){var e=this.jsVars.hcObj,c;if(e&&e.series&&(c=e.series[0])&&c.data&&c.data[b]&&c.data[b].slice)return c.data[c.xIncrement-1-b].slice()}}},ua);d("pie3d",
{defaultSeriesType:"pie",creditLabel:!1,defaultPlotShadow:0},d.pie2d);d("doughnut2d",{getPointColor:function(b,e,c){var a,b=C(b),e=ba(e);c<100&&ga?(a=Math.floor((85-0.2*(100-c))*100)/100,a=sa(b,a),c=Math.floor((100-0.5*c)*100)/100,b=ia(b,c),e={FCcolor:{color:a+t+b+t+b+t+a,alpha:e+t+e+t+e+t+e,radialGradient:!0}}):e=Q(b,e);return e}},d.pie2d);d("doughnut3d",{defaultPlotShadow:0},d.doughnut2d);d("pareto2d",{standaloneInit:!0,point:function(b,e,c,a,m){var g,f,i,k,h,d,n,p,w,q,r,j,s,z,G,b=c.length,A=0;
d={};s=m.chart.paletteIndex;var u=/3d$/.test(m.chart.defaultSeriesType),H=this.isBar,x=l(360-a.plotfillangle,90),I=o(a.plotborderthickness,1),B=m.chart.useRoundEdges,Ha=l(a.tooltipsepchar,", "),na=l(a.plotbordercolor,F.plotBorderColor[s]).split(t)[0],M=a.showplotborder==T?T:l(a.plotborderalpha,a.plotfillalpha,L),y=m.xAxis,oa=o(a.showcumulativeline,1),ca=m[D],wa=ca.axisGridManager,Ia=ca.x,U=a.showtooltip!=T,N=[],aa=[],Ja=o(a.use3dlighting,1),E=m[D].numberFormatter,O=o(a.showlinevalues,a.showvalues),
M=u?a.showplotborder?M:T:M,na=u?l(a.plotbordercolor,"#FFFFFF"):na;for(j=f=0;f<b;f+=1)if(r=c[f],c[f].vline)wa.addVline(y,r,j,m);else if(g=E.getCleanValue(r.value,!0),g!==null)r.value=g,N.push(r),j+=1;b=N.length;N.sort(function(a,b){return b.value-a.value});if(oa)n=o(a.linedashed,0),z=C(l(a.linecolor,F.plotBorderColor[s])),f=l(a.linealpha,100),p=o(a.linedashlen,5),w=o(a.linedashgap,4),d=o(a.linethickness,2),q={opacity:f/100},G=o(a.drawanchors,a.showanchors),G===void 0&&(G=f!=T),i=o(a.anchorborderthickness,
1),h=o(a.anchorsides,0),k=o(a.anchorradius,3),j=C(l(a.anchorbordercolor,z)),g=C(l(a.anchorbgcolor,F.anchorBgColor[s])),c=ba(l(a.anchoralpha,L)),r=ba(l(a.anchorbgalpha,c))*c/100,n=n?qa(p,w,d):void 0,d={yAxis:1,data:[],type:"line",color:Q(z,f),lineWidth:d,marker:{enabled:G,fillColor:Q(g,r),lineColor:Q(j,c),lineWidth:i,radius:k,symbol:ta(h)}};else{if(a.showsecondarylimits!=="1")a.showsecondarylimits="0";if(a.showdivlinesecondaryvalue!=="1")a.showdivlinesecondaryvalue="0"}for(f=0;f<b;f+=1)r=N[f],g=o(r.showlabel,
a.showlabels,1),c=S(!g?v:R(r.label,r.name)),wa.addXaxisCat(y,f,f,c),A+=g=r.value,i=l(r.color,m.colors[f%m.colors.length])+t+xa(a.plotgradientcolor,F.plotGradientColor[s]),k=l(r.alpha,a.plotfillalpha,L),h=l(r.ratio,a.plotfillratio),j={opacity:k/100},z=l(r.alpha,M)+v,i=za(i,k,h,x,B,na,z,H,u),e.data.push(pa(this.getPointStub(r,g,c,m),{y:g,shadow:j,color:i[0],borderColor:i[1],borderWidth:I,use3DLighting:Ja})),this.pointValueWatcher(m,g),oa&&aa.push({value:A,dataLabel:c,tooltext:P(r.tooltext)});Ia.catCount=
b;ca[1]||(ca[1]={});ca[1].stacking100Percent=!0;if(oa&&A>0){f=0;for(b=aa.length;f<b;f+=1)r=aa[f],m=e.data[f],g=r.value/A*100,s=E.percentValue(g),a=m.displayValue!==v?s:v,O==1&&(a=s),O==0&&(a=v),c=r.dataLabel,s=U?r.tooltext!==void 0?r.tooltext:(c!==v?c+Ha:v)+s:v,d.data.push({shadow:q,y:g,toolText:s,displayValue:a,link:m.link,dashStyle:n});return[e,d]}else return e},defaultSeriesType:"column",isDual:!0,creditLabel:!1},ua);d("pareto3d",{defaultSeriesType:"column3d",defaultPlotShadow:1},d.pareto2d);d("mscolumn2d",
{standaloneInit:!0,creditLabel:!1},d.mscolumn2dbase);d("mscolumn3d",{defaultSeriesType:"column3d",defaultPlotShadow:1},d.mscolumn2d);d("msbar2d",{isBar:!0,defaultSeriesType:"bar",spaceManager:d.barbase},d.mscolumn2d);d("msbar3d",{defaultSeriesType:"bar3d",defaultPlotShadow:1},d.msbar2d);d("msline",{standaloneInit:!0,creditLabel:!1},d.mslinebase);d("msarea",{standaloneInit:!0,creditLabel:!1},d.msareabase);d("stackedcolumn2d",{isStacked:!0},d.mscolumn2d);d("stackedcolumn3d",{isStacked:!0},d.mscolumn3d);
d("stackedbar2d",{isStacked:!0},d.msbar2d);d("stackedbar3d",{isStacked:!0},d.msbar3d);d("stackedarea2d",{isStacked:!0,showSum:0},d.msarea);d("marimekko",{isValueAbs:!0,distributedColumns:!0,isStacked:!0,xAxisMinMaxSetter:da,postSeriesAddition:function(b,e){var c=b[D],a,m,g,f,i,k=0;f=b.xAxis;var h,d=100/c.marimekkoTotal,n=[],l=b.series,p,q=0,r,j=o(e.chart.plotborderthickness,1),s=b.chart.rotateValues,v=o(e.chart.rotatexaxispercentvalues,0),j=j*-0.5-(j%2+(v?0:4)),G=v?3:0,A=s?270:0;m=c[0];var u=!m.stacking100Percent,
H=c.inCanvasStyle;c.isXYPlot=!0;c.distributedColumns=!0;f.min=0;f.max=100;f.labels.enabled=!1;f.gridLineWidth=0;f.alternateGridColor=ja;a=m.stack;e.chart.interactivelegend="0";i=0;for(m=b.xAxis.plotLines.length;i<m;i+=1)if(h=f.plotLines[i],h.isGrid)h.isCat=!0,n[h.value]=h;if(a.floatedcolumn&&(g=a.floatedcolumn[0])){a=0;for(m=g.length;a<m;){k+=f=g[a].p||0;p=f*d;h=q+p/2;r=q+p;for(i=0;i<l.length;i+=1)b.series[i].data[a]._FCX=q,b.series[i].data[a]._FCW=p;c.showStackTotal&&b.xAxis.plotLines.push({value:h,
width:0,isVline:u,isTrend:!u,label:{align:ka,textAlign:A,rotation:s?270:0,style:c.trendStyle,verticalAlign:Aa,y:0,x:0,text:c.numberFormatter.yAxis(ya(f,10))}});if(n[a])n[a].value=h,n[a]._weight=p;a+=1;c.showXAxisPercentValues&&a<m&&b.xAxis.plotLines.push({value:r,width:0,isVine:!0,label:{align:ka,textAlign:v?Ca:ka,rotation:v?270:0,style:{color:H.color,fontSize:H.fontSize,fontFamily:H.fontFamily,lineHeight:H.lineHeight,border:"1px solid",borderColor:H.color,backgroundColor:"#ffffff",backgroundOpacity:1},
verticalAlign:ea,y:j,x:G,text:c.numberFormatter.percentValue(r)},zIndex:5});q=r}}},defaultSeriesType:"floatedcolumn"},d.stackedcolumn2d);d("msstackedcolumn2d",{series:function(b,e,c){var a,m,g,f,i=e[D],k=0,h,l;h=[];var n;e.legend.enabled=Boolean(o(b.chart.showlegend,1));if(b.dataset&&b.dataset.length>0){this.categoryAdder(b,e);a=0;for(m=b.dataset.length;a<m;a+=1)if(n=b.dataset[a].dataset){g=0;for(f=n.length;g<f;g+=1,k+=1)h={data:[],stack:a},l=Math.min(i.oriCatTmp.length,n[g].data&&n[g].data.length),
h=this.point(c,h,n[g],b.chart,e,l,k,a),e.series.push(h)}if(this.isDual&&b.lineset&&b.lineset.length>0){g=0;for(f=b.lineset.length;g<f;g+=1,k+=1)h={data:[],yAxis:1,type:"line"},c=b.lineset[g],l=Math.min(i.oriCatTmp.length,c.data&&c.data.length),e.series.push(d.msline.point.call(this,"msline",h,c,b.chart,e,l,k))}this.configureAxis(e,b);b.trendlines&&fa(b.trendlines,e.yAxis,e[D],!1,this.isBar)}}},d.stackedcolumn2d);d("mscombi2d",{series:function(b,e,c){var a,m,g,f,i=b.chart,k,h=[],J=[],n=[],p,w,q=e[D],
r=this.isDual;e.legend.enabled=Boolean(o(b.chart.showlegend,1));if(b.dataset&&b.dataset.length>0){this.categoryAdder(b,e);f=q.oriCatTmp.length;a=0;for(m=b.dataset.length;a<m;a+=1){g=b.dataset[a];p=r&&l(g.parentyaxis,"p").toLowerCase()==="s"?!0:!1;k={data:[]};if(p)k.yAxis=1;w=R(g.renderas,v).toLowerCase();switch(w){case "line":k.type="line";h.push(d.msline.point.call(this,c,k,g,i,e,f,a));break;case "area":k.type="area";e.chart.series2D3Dshift=!0;n.push(d.msarea.point.call(this,c,k,g,i,e,f,a));break;
case "column":J.push(d.mscolumn2d.point.call(this,c,k,b.dataset[a],i,e,f,a));break;default:p?(k.type="line",h.push(d.msline.point.call(this,c,k,g,i,e,f,a))):J.push(d.mscolumn2d.point.call(this,c,k,b.dataset[a],i,e,f,a))}}e.series=i.areaovercolumns!=="0"?e.series.concat(J,n,h):e.series.concat(n,J,h);if(J.length===0)q.hasNoColumn=!0;this.configureAxis(e,b);b.trendlines&&fa(b.trendlines,e.yAxis,e[D],!1,this.isBar)}}},d.mscolumn2d);d("mscombi3d",{series:d.mscombi2d.series,eiMethods:"view2D,view3D,resetView,rotateView,getViewAngles,fitToStage"},
d.mscolumn3d);d("mscolumnline3d",{},d.mscombi3d);d("stackedcolumn2dline",{isStacked:!0,stack100percent:0},d.mscombi2d);d("stackedcolumn3dline",{isStacked:!0,stack100percent:0},d.mscombi3d);d("mscombidy2d",{isDual:!0},d.mscombi2d);d("mscolumn3dlinedy",{isDual:!0},d.mscolumnline3d);d("stackedcolumn3dlinedy",{isDual:!0},d.stackedcolumn3dline);d("msstackedcolumn2dlinedy",{isDual:!0,stack100percent:0},d.msstackedcolumn2d);d("scrollcolumn2d",{postSeriesAddition:d.scrollbase.postSeriesAddition,avgScrollPointWidth:40},
d.mscolumn2d);d("scrollline2d",{postSeriesAddition:d.scrollbase.postSeriesAddition,avgScrollPointWidth:75},d.msline);d("scrollarea2d",{postSeriesAddition:d.scrollbase.postSeriesAddition,avgScrollPointWidth:75},d.msarea);d("scrollstackedcolumn2d",{postSeriesAddition:function(b,e,c,a){d.base.postSeriesAddition.call(this,b,e,c,a);d.scrollbase.postSeriesAddition.call(this,b,e,c,a)},avgScrollPointWidth:75},d.stackedcolumn2d);d("scrollcombi2d",{postSeriesAddition:d.scrollbase.postSeriesAddition,avgScrollPointWidth:40},
d.mscombi2d);d("scrollcombidy2d",{postSeriesAddition:d.scrollbase.postSeriesAddition,avgScrollPointWidth:40},d.mscombidy2d);d("scatter",{standaloneInit:!0,defaultSeriesType:"scatter",creditLabel:!1},d.scatterbase);d("bubble",{standaloneInit:!0,standaloneInut:!0,defaultSeriesType:"bubble",point:function(b,e,c,a,m,g,f){if(c.data){var i,k,h,J,n,p,v,q,r,j,s=!1,z,G,b=d[b],g=c.data,A=g.length,u=m[D],H=o(c.showvalues,u.showValues);h=o(a.bubblescale,1);var x=l(a.negativecolor,"FF0000"),I=m.plotOptions.bubble,
u=u.numberFormatter,B=o(c.showregressionline,a.showregressionline,0);I.bubbleScale=h;e.name=P(c.seriesname);if(o(c.includeinlegend)===0||e.name===void 0)e.showInLegend=!1;h=Boolean(o(c.drawanchors,c.showanchors,a.drawanchors,1));v=l(c.plotfillalpha,c.bubblefillalpha,a.plotfillalpha,L);q=o(c.showplotborder,a.showplotborder,1);r=C(l(c.plotbordercolor,a.plotbordercolor,"666666"));i=l(c.plotborderthickness,a.plotborderthickness,1);j=l(c.plotborderalpha,a.plotborderalpha,"95");q=q==1?i:0;f=l(c.color,c.plotfillcolor,
a.plotfillcolor,m.colors[f%m.colors.length]);e.marker={enabled:h,fillColor:this.getPointColor(f,L),lineColor:{FCcolor:{color:r,alpha:j}},lineWidth:q,symbol:"circle"};if(B){e.events={hide:this.hideRLine,show:this.showRLine};var t={sumX:0,sumY:0,sumXY:0,sumXsqure:0,sumYsqure:0,xValues:[],yValues:[]},F=o(c.showyonx,a.showyonx,1),M=C(l(c.regressionlinecolor,a.regressionlinecolor,f)),y=o(c.regressionlinethickness,a.regressionlinethickness,1);i=ba(o(c.regressionlinealpha,a.regressionlinealpha,100));M=Q(M,
i)}for(k=0;k<A;k+=1)if(J=g[k])if(i=u.getCleanValue(J.y),z=u.getCleanValue(J.x),G=u.getCleanValue(J.z,!0),i===null)e.data.push({y:null,x:z});else{s=!0;n=C(l(J.color,J.z<0?x:f));p=l(J.alpha,v);J=b.getPointStub(J,i,z,m,c,H);n=o(a.use3dlighting)===0?n:b.getPointColor(n,p);if(G!==null)I.zMax=I.zMax>G?I.zMax:G,I.zMin=I.zMin<G?I.zMin:G;e.data.push({y:i,x:z,z:G,displayValue:J.displayValue,toolText:J.toolText,link:J.link,marker:{enabled:h,fillColor:n,lineColor:{FCcolor:{color:r,alpha:j}},lineWidth:q,symbol:"circle"}});
this.pointValueWatcher(m,i,z,B&&t)}else e.data.push({y:null});B&&(c={type:"line",color:M,showInLegend:!1,lineWidth:y,enableMouseTracking:!1,marker:{enabled:!1},data:this.getRegressionLineSeries(t,F,A),zIndex:0},e=[e,c])}if(!s)e.showInLegend=!1;return e},getPointStub:function(b,e,c,a,m,g){var a=a[D],e=e===null?e:a.numberFormatter.dataLabels(e),f,i=a.tooltipSepChar;a.showTooltip?P(b.tooltext)!==void 0?m=S(b.tooltext):e===null?m=!1:(a.seriesNameInToolTip&&(f=l(m&&m.seriesname)),m=f?f+i:v,m+=c?c+i:v,
m+=e,m+=b.z?i+b.z:v):m=v;c=o(b.showvalue,g,a.showValues)?l(b.name,b.label)!==void 0?S(l(b.name,b.label)):e:v;b=P(b.link);return{displayValue:c,toolText:m,link:b}}},d.scatter);d("zoomline",{standaloneInit:!0,hasVDivLine:!0,defaultSeriesType:"stepzoom",xAxisMinMaxSetter:function(b){this.base.xAxisMinMaxSetter.apply(this,arguments);var e=b.xAxis,c=b[D].x;e.min=0;e.max=c.catCount-1},series:function(b,e,c){var a,m,g=e[D],f;a=b.chart;o(a.compactdatamode,0);e.legend.enabled=Boolean(o(a.showlegend,1));if(b.dataset&&
b.dataset.length>0){b.categories&&b.categories[0]&&b.categories[0].category&&this.categoryAdder(b,e);a=0;for(m=b.dataset.length;a<m;a+=1)if(b.dataset[a].data)f={data:[]},f=this.point(c,f,b.dataset[a],b.chart,e,g.oriCatTmp.length,a),f instanceof Array?e.series=e.series.concat(f):e.series.push(f),f=!0;f&&(this.configureAxis(e,b),b.trendlines&&!this.isLog&&fa(b.trendlines,e.yAxis,g,!1,this.isBar))}},point:function(b,e,c,a,m,g,f){var i,k,h,d,n,p,w,q,r,j,s,z,G,A,u,H,x,I,B=c.data,t=m[D],b=l(e.type,this.defaultSeriesType),
F=m.plotOptions[b]&&m.plotOptions[b].stacking,M=l(this.isValueAbs,t.isValueAbs,!1);o(c.showvalues,t.showValues);var y=o(e.yAxis,0),t=t.numberFormatter;q=o(a.compactdatamode,0);I=l(a.dataseparator,"|");i=l(c.seriesname,v);k=C(l(c.color,a.linecolor,m.colors[f%m.colors.length]));h=l(c.alpha,a.linealpha,L);d=o(c.linethickness,a.linethickness,2);n=Boolean(o(c.dashed,a.linedashed,0));p=o(c.linedashlen,a.linedashlen,5);w=o(c.linedashgap,a.linedashgap,4);o(c.includeinlegend,1);l(c.valueposition,a.valueposition);
f=o(c.showvalues,a.showvalues);r=o(c.drawanchors,c.showanchors,a.drawanchors,a.showanchors);j=o(c.anchorsides,a.anchorsides,0);s=o(c.anchorradius,a.anchorradius,3);z=C(l(c.anchorbordercolor,a.anchorbordercolor,k));G=o(c.anchorborderthickness,a.anchorborderthickness,1);A=C(l(c.anchorbgcolor,a.anchorbgcolor,k));u=l(c.anchoralpha,a.anchoralpha,L);a=l(c.anchorbgalpha,a.anchorbgalpha,u);e.marker={enabled:r===void 0?h!=0:!!r,fillColor:{FCcolor:{color:A,alpha:a*u/100+v}},lineColor:{FCcolor:{color:z,alpha:u+
v}},lineWidth:G,radius:s,symbol:ta(j)};e.name=i;e.color={FCcolor:{color:k,alpha:h}};e.lineWidth=d;e.dashStyle=n?qa(p,w,d):void 0;if(o(c.includeinlegend)===0||e.name===void 0||h==0&&r!==1)e.showInLegend=!1;if(B)if(q){B=B[0].split(I);d={opacity:x/100};for(c=0;c<g;c+=1)i=t.getCleanValue(B[c]),i===null?e.data.push({y:null}):(H=!0,q=t.dataLabels(i),n=f?q:v,e.data.push({y:i,displayValue:n,shadow:d,toolText:q}),this.pointValueWatcher(m,i,y,F,c,0,b))}else for(c=0;c<g;c+=1)(x=B[c])?(i=t.getCleanValue(x.value,
M),i===null?e.data.push({y:null}):(H=!0,o(x.anchorsides,j),o(x.anchorradius,s),C(l(x.anchorbordercolor,z)),o(x.anchorborderthickness,G),C(l(x.anchorbgcolor,A)),l(x.anchoralpha,u),l(x.anchorbgalpha,a),I=C(l(x.color,k)),x=l(x.alpha,h),d={opacity:x/100},q=t.dataLabels(i),n=f?q:v,e.data.push({displayValue:n,toolText:q,y:i,shadow:d,color:{FCcolor:{color:I,alpha:x}}}),this.pointValueWatcher(m,i,y,F,c,0,b))):e.data.push({y:null});if(!H)e.showInLegend=!1;return e},categoryAdder:function(b,e){var c,a=0,m,
g=e[D],f=g.axisGridManager,i=e.xAxis,d,h=g.x,p=b.chart,n=e.chart,$=o(p.showlabels,1);n.zoomType="x";i.maxZoom=2;m=o(p.pixelsperpoint,15);m<=0&&(m=15);g.pixelsperpoint=m;if(b.categories&&b.categories[0]&&b.categories[0].category){if(b.categories[0].font)e.xAxis.labels.style.fontFamily=b.categories[0].font;if((m=o(b.categories[0].fontsize))!==void 0)m<1&&(m=1),e.xAxis.labels.style.fontSize=m+ma,ha(e.xAxis.labels.style);if(b.categories[0].fontcolor)e.xAxis.labels.style.color=b.categories[0].fontcolor.split(t)[0].replace(/^\#?/,
"#");m=e[D].oriCatTmp;var w=b.categories[0].category;c=o(p.compactdatamode,0);d=l(p.dataseparator,"|");if(c){f=w[0].split(d);for(c=0;c<f.length;c+=1)d=S(R(f[c],f[c].name)),m[a]=d,a+=1}else for(c=0;c<w.length;c+=1)w[c].vline?f.addVline(i,w[c],a):(d=w[c].showlabel==="0"?v:S(R(b.categories[0].category[c].label,b.categories[0].category[c].name)),m[a]=R(S(b.categories[0].category[c].tooltext),d),a+=1);if($)i.categories=m}i=a-1;g.displayStartIndex=o(p.displaystartindex,0);g.displayEndIndex=o(p.displayendindex,
i);if(g.displayStartIndex<0||g.displayStartIndex>=i)g.displayStartIndex=0;if(g.displayEndIndex<=g.displayStartIndex||g.displayEndIndex>i)g.displayEndIndex=i;h.catCount=a;n.hasScroll=!0;a=n.stepZoom={};a.pixelsperpoint=g.pixelsperpoint;a.displayStartIndex=g.displayStartIndex;a.displayEndIndex=g.displayEndIndex;a.scrollColor=C(l(p.scrollcolor,F.altHGridColor[e.chart.paletteIndex]));a.scrollHeight=o(p.scrollheight,16);a.scrollPadding=o(p.scrollpadding,e.chart.plotBorderWidth);a.scrollBtnWidth=o(p.scrollbtnwidth,
p.scrollheight,16);a.scrollBtnPadding=o(p.scrollbtnpadding,0);g.marginBottomExtraSpace+=a.scrollPadding+a.scrollHeight},placeHorizontalAxis:function(b,e,c,a,d,g,f){var i=c[D],k,h,l,n,p,w,q,r,j=0,s=0,z=10,t=1,A=0,u=0,H=!1,x=!1,I=!1;h=o(a.chart.labelstep,0);r=e.labelDisplay;var B=e.horizontalLabelPadding,C=i.marginBottomExtraSpace,F=c.chart.marginLeft,M=c.chart.marginRight,y=i.smartLabel,L=i.pixelsperpoint,S=e.catCount,T=e.slantLabels,Q=d/(b.max-b.min),U=0,N=0,u={w:0,h:0};if(b.labels.style)p=b.labels.style,
y.setStyle(p),q=y.getOriSize("W"),z=q.height,w=q.width+4,n=y.getOriSize("WWW").width+4;var aa,R,E,O=[],Y=[],V=0,W=0,K,Z=e.horizontalAxisNamePadding;E=0;var e=e.staggerLines,X=U;if(b.title&&b.title.text!=v)p=b.title.style,y.setStyle(p),A=y.getOriSize("W").height,b.title.rotation=0,l=y.getSmartText(b.title.text,d,g),s=l.height;c.chart.marginLeft!=parseInt(a.chart.chartleftmargin,10)&&(k=!0);c.chart.marginRight!=parseInt(a.chart.chartrightmargin,10)&&(R=!0);E=d-f;switch(r){case "none":H=I=!0;break;case "rotate":j=
T?300:270;q=z;z=w;w=q;H=!0;break;case "stagger":x=H=!0,a=Math.floor((g-A)/z),a<e&&(e=a)}q=0;l=b.plotLines;for(a=l.length;q<a;q+=1)(f=l[q])&&f.label&&typeof f.label.text!==va&&Y.push(f);l=b.plotBands;q=0;for(a=l.length;q<a;q+=1)(f=l[q])&&f.label&&typeof f.label.text!==va&&Y.push(f);l=b.categories||[];q=0;for(a=l.length;q<a;q+=1)O.push({value:q,label:{text:l[q]}});f=O.length-1;a=O.length;x&&(e>a?e=a:e<2&&(e=2));if(a){b.scroll&&b.scroll.viewPortMin&&b.scroll.viewPortMax?(aa=b.scroll.viewPortMin,q=b.scroll.viewPortMax,
R=k=!1):(aa=b.min,q=b.max);K=(O[f].value-O[0].value)*Q;K=Math.max(L,K/(S-1));L=(O[0].value-aa)*Q;Q*=q-O[f].value;r==="auto"?K<n&&(j=T?300:270,q=z,z=w,w=q,H=!0):r==="stagger"&&(K*=e);r=(L+F)*2;if((n=l[0].label)&&n.text)n.style&&y.setStyle(n.style),n=Math.min(K,y.getOriSize(n.text).width+4),n>r&&(V=(n-r)/2);r=(Q+M)*2;if((n=l[f].label)&&n.text)n.style&&y.setStyle(n.style),n=Math.min(K,y.getOriSize(n.text).width+4),n>r&&(W=(n-r)/2);M=V+W;M>0&&(E>M?(k=(k=W*d/(W+d))?k+4:0,c.chart.marginRight+=k,d-=k,k=
(k=V*d/(V+d))?k+4:0,c.chart.marginLeft+=k,d-=k):V<W?E>=W&&R?(k=(k=W*d/(W+d))?k+4:0,c.chart.marginRight+=k,d-=k):k&&(k=(k=V*d/(V+d))?k+4:0,c.chart.marginLeft+=k,d-=k):E>=V&&k?(k=(k=V*d/(V+d))?k+4:0,c.chart.marginLeft+=k,d-=k):R&&(k=(k=W*d/(W+d))?k+4:0,c.chart.marginRight+=k,d-=k));!x&&!I&&(h?(K*=h,K=Math.max(K,w)):(t=Math.ceil(w/K),K*=t));for(h=0;h<a;h+=1)if(f=O[h],h%t&&f.label)f.label.text=v;else if(f&&f.label&&P(f.label.text)!==void 0){n=f.label;if(n.style&&n.style!==p)p=n.style,y.setStyle(p);if(!I)k=
j||x?y.getOriSize(n.text):y.getSmartText(n.text,K-4,g,H),u.w=Math.max(u.w,k.width+4),u.h=Math.max(u.h,k.height)}}h=0;for(a=Y.length;h<a;h+=1)if((f=Y[h])&&f.label&&P(f.label.text)!==void 0){n=f.label;if(n.style&&n.style!==p)p=n.style,y.setStyle(p);k=y.getOriSize(n.text);n.verticalAlign===ea?U=mathMax(U,k.height):N=mathMax(N,k.height)}b.scroll&&b.scroll.enabled&&!j&&!I&&(h=u.w/2,c.chart.marginLeft<h&&(k=h-c.chart.marginLeft,E>k&&(d-=k,E-=k,c.chart.marginLeft+=k)),c.chart.marginRight<h&&(k=h-c.chart.marginRight,
E>k&&(d-=k,c.chart.marginRight+=k)));E=I?z:j?u.w:x?e*z:u.h;E>0&&(X+=B+E);s>0&&(X+=s+Z);u=N+X+2;u>g&&(g=u-g,Z>g?(Z-=g,g=0):(g-=Z,Z=0,B>g?(B-=g,g=0):(g-=B,B=0)),N>g?N-=g:(N>0&&(g-=N,N=0),g>0&&(U>g?U-=g:(U>0&&(g-=U,U=0),g>0&&((q=s-A)>g?s-=g:(g-=q,s=A,g>0&&((q=E-z)>g?E-=g:(g-=q,E=z,g>0&&(g-=s+Z,s=0,g>0&&(g-=E,E=0,g>0&&(B-=g)))))))))));B+=C;A=E+B;g=i.is3d?-c.chart.xDepth:0;a=O.length;u=0;j?(z=K,x=E-4,b.labels.rotation=j,b.labels.align="right",b.labels.y=B-C+4,b.labels.x=w/2):x?x=K*e-4:(z=E,x=K-4);for(h=
0;h<a;h+=t)if((f=O[h])&&f.label&&P(f.label.text)!==void 0){n=f.label;if(n.style&&n.style!==p)p=n.style,y.setStyle(p);if(!I)k=y.getSmartText(n.text,x,z,H),b.categories[h]=k.text;u+=1}a=Y.length;for(h=j=w=0;h<a;h+=1)if((f=Y[h].plotObj?Y[h].plotObj:Y[h])&&f.label&&P(f.label.text)!==void 0){n=f.label;if(n.style&&n.style!==p)p=n.style,y.setStyle(p);n.verticalAlign===ea?(k=y.getSmartText(n.text,d,U,!0),j=Math.max(j,k.height),n.text=k.text,n.y=A+y.getOriSize("W").height,n.x=g):(k=y.getSmartText(n.text,d,
N,!0),w=Math.max(w,k.height),n.text=k.text,n.y=-(N-y.getOriSize("W").height+B+2))}if(s>0)y.setStyle(b.title.style),l=y.getSmartText(b.title.text,d,s),b.title.text=l.text,b.title.margin=A+j+Z;X=j;if(E>0)i.horizontalAxisHeight=B+E-C,X+=i.horizontalAxisHeight;s>0&&(X+=s+Z);c.chart.marginBottom+=X;w>0&&(c.chart.marginTop+=w,X+=w);return X},creditLabel:!1,defaultPlotShadow:1},d.msline);d("ssgrid",{standaloneInit:!0,defaultSeriesType:"ssgrid",chart:function(b,e,c,a,d,g){c=pa({},c);c.chart=c.chart||c.graph||
{};delete c.graph;var f,i,k,h=0,t,n,e=[],a=c.chart,D=c.data,w=D&&D.length,c=this.smartLabel,q=new p.NumberFormatter(a,this.name),r=b.offsetHeight,d=b.offsetWidth,j={},s=h=0,h=(a.palette>0&&a.palette<6?a.palette:o(this.paletteIndex,1))-1,b={chart:{renderTo:b,ignoreHiddenSeries:!1,events:{},spacingTop:0,spacingRight:0,spacingBottom:0,spacingLeft:0,marginTop:0,marginRight:0,marginBottom:0,marginLeft:0,borderRadius:0,borderColor:"#000000",borderWidth:1,defaultSeriesType:"ssgrid",style:{fontFamily:l(a.basefont,
"Verdana"),fontSize:ra(a.basefontsize,20)+ma,color:l(a.basefontcolor,F.baseFontColor[h]).replace(/^#?([a-f0-9]+)/ig,"#$1")},plotBackgroundColor:ja},labels:{smartLabel:c},colors:["AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D","A186BE","CC6600","FDC689","ABA000","F26D7D","FFF200","0054A6","F7941C","CC3300","006600","663300","6DCFF6"],credits:{href:"http://www.fusioncharts.com?BS=FCHSEvalMark",text:"FusionCharts",enabled:this.creditLabel},legend:{enabled:!1},
series:[],subtitle:{text:v},title:{text:v},tooltip:{enabled:!1},exporting:{buttons:{exportButton:{},printButton:{enabled:!1}}}},z=b.colors,G=b.colors.length,A=0,u=t=i=0,s=f=n=0;i=g.jsVars.cfgStore;g=b.chart;ha(b.chart.style);j.showPercentValues=o(i.showpercentvalues,a.showpercentvalues,0);j.numberItemsPerPage=l(i.numberitemsperpage,a.numberitemsperpage);j.showShadow=o(i.showshadow,a.showshadow,0);j.baseFont=l(i.basefont,a.basefont,"Verdana");k=ra(i.basefontsize,a.basefontsize,10);j.baseFontSize=k+
ma;j.baseFontColor=C(l(i.basefontcolor,a.basefontcolor,F.baseFontColor[h]));j.alternateRowBgColor=C(l(i.alternaterowbgcolor,a.alternaterowbgcolor,F.altHGridColor[h]));j.alternateRowBgAlpha=l(i.alternaterowbgalpha,a.alternaterowbgalpha,F.altHGridAlpha[h])+v;j.listRowDividerThickness=o(i.listrowdividerthickness,a.listrowdividerthickness,1);j.listRowDividerColor=C(l(i.listrowdividercolor,a.listrowdividercolor,F.borderColor[h]));j.listRowDividerAlpha=o(i.listrowdivideralpha,a.listrowdivideralpha,F.altHGridAlpha[h])+
15+v;j.colorBoxWidth=o(i.colorboxwidth,a.colorboxwidth,8);j.colorBoxHeight=o(i.colorboxheight,a.colorboxheight,8);j.navButtonRadius=o(i.navbuttonradius,a.navbuttonradius,7);j.navButtonColor=C(l(i.navbuttoncolor,a.navbuttoncolor,F.canvasBorderColor[h]));j.navButtonHoverColor=C(l(i.navbuttonhovercolor,a.navbuttonhovercolor,F.altHGridColor[h]));j.textVerticalPadding=o(i.textverticalpadding,a.textverticalpadding,3);j.navButtonPadding=o(i.navbuttonpadding,a.navbuttonpadding,5);j.colorBoxPadding=o(i.colorboxpadding,
a.colorboxpadding,10);j.valueColumnPadding=o(i.valuecolumnpadding,a.valuecolumnpadding,10);j.nameColumnPadding=o(i.namecolumnpadding,a.namecolumnpadding,5);j.borderThickness=o(i.borderthickness,a.borderthickness,1);j.borderColor=C(l(i.bordercolor,a.bordercolor,F.borderColor[h]));j.borderAlpha=l(i.borderalpha,a.borderalpha,F.borderAlpha[h])+v;j.bgColor=l(i.bgcolor,a.bgcolor,"FFFFFF");j.bgAlpha=l(i.bgalpha,a.bgalpha,L);j.bgRatio=l(i.bgratio,a.bgratio,L);j.bgAngle=l(i.bgangle,a.bgangle,T);g.borderRadius=
j.borderThickness/16;g.borderWidth=j.borderThickness;g.borderColor={FCcolor:{color:j.borderColor,alpha:j.borderAlpha}};g.backgroundColor={FCcolor:{color:j.bgColor,alpha:j.bgAlpha,ratio:j.bgRatio,angle:j.bgAngle}};k={fontFamily:j.baseFont,fontSize:j.baseFontSize,color:j.baseFontColor};ha(k);c.setStyle(k);for(h=0;h<w;h+=1)if(f=D[h],t=q.getCleanValue(f.value),n=S(R(f.label,f.name)),i=C(l(f.color,z[h%G])),l(f.alpha,a.plotfillalpha,L),n!=v||t!=null)e.push({value:t,label:n,color:i}),A+=t,s+=1;for(h=0;h<
s;h+=1)f=e[h],t=f.value,f.dataLabel=f.label,f.displayValue=j.showPercentValues?q.percentValue(t/A*100):q.dataLabels(t),D=c.getOriSize(f.displayValue),u=Math.max(u,D.width+j.valueColumnPadding);j.numberItemsPerPage?j.numberItemsPerPage>=s?(j.numberItemsPerPage=s,t=r/j.numberItemsPerPage,i=s):(q=r,q-=2*(j.navButtonPadding+j.navButtonRadius),i=j.numberItemsPerPage,t=q/i):(h=parseInt(k.lineHeight,10),h+=2*j.textVerticalPadding,h=Math.max(h,j.colorBoxHeight),r/h>=s?(t=r/s,i=s):(q=r,q-=2*(j.navButtonPadding+
j.navButtonRadius),i=Math.floor(q/h),t=q/i));n=d-j.colorBoxPadding-j.colorBoxWidth-j.nameColumnPadding-u-j.valueColumnPadding;f=j.colorBoxPadding+j.colorBoxWidth+j.nameColumnPadding;g.height=r;g.width=d;g.rowHeight=t;g.labelX=f;g.colorBoxWidth=j.colorBoxWidth;g.colorBoxHeight=j.colorBoxHeight;g.colorBoxX=j.colorBoxPadding;g.valueX=j.colorBoxPadding+j.colorBoxWidth+j.nameColumnPadding+n+j.valueColumnPadding;g.valueColumnPadding=j.valueColumnPadding;g.textStyle=k;g.listRowDividerAttr={"stroke-width":j.listRowDividerThickness,
stroke:{FCcolor:{color:j.listRowDividerColor,alpha:j.listRowDividerAlpha}}};g.alternateRowColor={FCcolor:{color:j.alternateRowBgColor,alpha:j.alternateRowBgAlpha}};g.navButtonRadius=j.navButtonRadius;g.navButtonPadding=j.navButtonPadding;g.navButtonColor=j.navButtonColor;g.navButtonHoverColor=j.navButtonHoverColor;g.lineHeight=parseInt(k.lineHeight,10);r=[];j=0;q=!0;for(h=0;h<s&i!=0;h+=1)h%i==0&&(r.push({data:[],visible:q}),q=!1,j+=1),f=e[h],r[j-1].data.push({label:c.getSmartText(f.dataLabel,n,t).text,
displayValue:f.displayValue,y:f.value,color:f.color});b.series=r;b.exporting.enabled=a.exportenabled=="1"?!0:!1;b.exporting.buttons.exportButton.enabled=a.exportshowmenuitem=="0"?!1:!0;b.exporting.filename=a.exportfilename?a.exportfilename:"FusionCharts";b.exporting.width=d;return b},creditLabel:!1},d.base)}})();
