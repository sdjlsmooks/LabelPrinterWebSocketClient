$scanners=(get-childitem C:\TempScannerLogs\HS-*.txt -exclude *error*)
$total=0
foreach ($file in $scanners) {
   $number = select-string Found $file | measure-object -line
   $total+=$number.Lines
   write-host $file " : " $number.Lines
}
write-host $allTraffic
write-host "Total: " $total

