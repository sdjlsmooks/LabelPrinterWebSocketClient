$scanners=(get-childitem C:\TempScannerLogs\HS-*.txt -exclude *error*)
$total=0
foreach ($file in $scanners) {
   $number = select-string Found $file | measure-object -line
   $total+=$number.Lines
   Write-Host -NoNewLine $file " :`t`t" 
   if ($file.FullName.Length -le 42) {
       Write-Host -NoNewLine "`t"
   }
   Write-Host $number.Lines
}
Write-Host $allTraffic
Write-Host "Total: " $total
Write-Host ""
