Stop-Service -DisplayName HS-AOPBACKDOOR
Stop-Service -DisplayName HS-AOPFrontDesk
Stop-Service -DisplayName HS-CARETEAM
Stop-Service -DisplayName HS-ClinicalSVCBackDoor
Stop-Service -DisplayName HS-Crestone1LabelPrinter
Stop-Service -DisplayName HS-Crestone2LabelPrinter
Stop-Service -DisplayName HS-CRISISGlassCube
Stop-Service -DisplayName HS-CRISISSTAFF
Stop-Service -DisplayName HS-CWCFrontDeskLabelPrinter
Stop-Service -DisplayName HS-DISCOVERYCENTER
Stop-Service -DisplayName HS-EDUCATIONALSERVICES
Stop-Service -DisplayName HS-LearningCenter
Stop-Service -DisplayName HS-MARCLabelPrinter
Stop-Service -DisplayName HS-MedicalCenterLabelPrinter
Stop-Service -DisplayName HS-RecoveryCenter
Stop-Service -DisplayName HS-Suite200LabelPrinter
Stop-Service -DisplayName HS-YFSBACKDOOR
Stop-Service -DisplayName HS-YFSDUTCHCLARKLabelPrinter
Stop-Service -DisplayName HS-YFSFrontDeskLabelPrinter
$date = Get-Date
if ($date.Hour -eq 0) {
   Remove-Item C:\TempScannerLogs\HS-*
}
Restart-Service -Name Spooler -Force
Start-Service -DisplayName HS-AOPBACKDOOR
Start-Service -DisplayName HS-AOPFrontDesk
Start-Service -DisplayName HS-CARETEAM
Start-Service -DisplayName HS-ClinicalSVCBackDoor
Start-Service -DisplayName HS-Crestone1LabelPrinter
Start-Service -DisplayName HS-Crestone2LabelPrinter
Start-Service -DisplayName HS-CRISISGlassCube
Start-Service -DisplayName HS-CRISISSTAFF
Start-Service -DisplayName HS-CWCFrontDeskLabelPrinter
Start-Service -DisplayName HS-DISCOVERYCENTER
Start-Service -DisplayName HS-EDUCATIONALSERVICES
Start-Service -DisplayName HS-LearningCenter
Start-Service -DisplayName HS-MARCLabelPrinter
Start-Service -DisplayName HS-MedicalCenterLabelPrinter
Start-Service -DisplayName HS-RecoveryCenter
Start-Service -DisplayName HS-Suite200LabelPrinter
Start-Service -DisplayName HS-YFSBACKDOOR
Start-Service -DisplayName HS-YFSDUTCHCLARKLabelPrinter
Start-Service -DisplayName HS-YFSFrontDeskLabelPrinter

