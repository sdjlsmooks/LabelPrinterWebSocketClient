Restart-Service -Name Spooler -Force
Stop-Service -DisplayName HS-AOPBACKDOOR
$date = Get-Date
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-AOPBACKDOOR*
}
Start-Service -DisplayName HS-AOPBACKDOOR
Stop-Service -DisplayName HS-AOPFrontDesk
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-AOPFrontDesk*
}
Start-Service -DisplayName HS-AOPFrontDesk
Stop-Service -DisplayName HS-ClinicalSVCBackDoor
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-ClinicalSVCBackDoor*
}
Start-Service -DisplayName HS-ClinicalSVCBackDoor
Stop-Service -DisplayName HS-CARETEAM
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-CARETEAM*
}
Start-Service -DisplayName HS-CARETEAM
Stop-Service -DisplayName HS-ClinicalSVCBackDoor
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-ClinicalSVCBackDoor*
}
Start-Service -DisplayName HS-ClinicalSVCBackDoor
Stop-Service -DisplayName HS-Crestone1LabelPrinter
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-CrestoneMainDesk*
}
Start-Service -DisplayName HS-Crestone1LabelPrinter
Stop-Service -DisplayName HS-CrestoneHallwayLabelPrinter
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-Crestone_Hallway*
}
Start-Service -DisplayName HS-CrestoneHallwayLabelPrinter
Stop-Service -DisplayName HS-CRISISGlassCube
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-CRISISGlassCube*
}
Start-Service -DisplayName HS-CRISISGlassCube
Stop-Service -DisplayName HS-CRISISSTAFF
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-CRISISSTAFF*
}
Start-Service -DisplayName HS-CRISISSTAFF
Stop-Service -DisplayName HS-CWCFrontDeskLabelPrinter
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-CWCFrontDeskLabelPrinter*
}
Start-Service -DisplayName HS-CWCFrontDeskLabelPrinter
Stop-Service -DisplayName HS-DISCOVERYCENTER
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-DISCOVERYCENTER*
}
Start-Service -DisplayName HS-DISCOVERYCENTER
Stop-Service -DisplayName HS-EDUCATIONALSERVICES
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-EDUCATIONALSERVICES*
}
Start-Service -DisplayName HS-EDUCATIONALSERVICES
Stop-Service -DisplayName HS-LearningCenter
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-LearningCenter*
}
Start-Service -DisplayName HS-LearningCenter
Stop-Service -DisplayName HS-MARCLabelPrinter
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-MARCLabelPrinter*
}
Start-Service -DisplayName HS-MARCLabelPrinter
Stop-Service -DisplayName HS-MedicalCenterLabelPrinter
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-MedicalCenterLabelPrinter*
}
Start-Service -DisplayName HS-MedicalCenterLabelPrinter
Stop-Service -DisplayName HS-RecoveryCenter
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-RecoveryCenter*
}
Start-Service -DisplayName HS-RecoveryCenter
Stop-Service -DisplayName HS-Suite200LabelPrinter
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-Suite200LabelPrinter*
}
Start-Service -DisplayName HS-Suite200LabelPrinter
Stop-Service -DisplayName HS-YFSBACKDOOR
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-YFSBACKDOOR*
}
Start-Service -DisplayName HS-YFSBACKDOOR
Stop-Service -DisplayName HS-YFSDUTCHCLARKLabelPrinter
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-YFSDutchClarkFrontDesk*.*
}
Start-Service -DisplayName HS-YFSDUTCHCLARKLabelPrinter
Stop-Service -DisplayName HS-YFSClinicalServicesLabelPrinter
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-ClinicalSVCFront*
}
Start-Service -DisplayName HS-YFSClinicalServicesLabelPrinter
Restart-Service -name Spooler -force
Get-Date >> c:\TempScannerLogs\RestartLastRun.txt
