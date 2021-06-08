function getPrinterTraffic() 
{
     $scanners=(get-childitem C:\TempScannerLogs\HS-*.txt -exclude *error*)
     $total=0
     foreach ($file in $scanners) {
        $number = select-string Found $file | measure-object -line
        $total+=$number.Lines
        Write-Host -NoNewLine $file " :`t`t"
        if ($file.FullName.Length -le 35) {
            Write-Host -NoNewLine "`t"
        }
        if ($file.FullName.Length -le 42) {
            Write-Host -NoNewLine "`t"
        }
        $nlf = ""+"{0,5}" -f $number.Lines
        Write-Host $nlf
     }
     Write-Host $allTraffic
     Write-Host "Total: " $total
     Write-Host ""
}

function gtf() {
   for(;;)  {
      clear;
      getPrinterTraffic;
      start-sleep -seconds 15
   }
}

getPrinterTraffic;

