function stoplp() {
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
   Remove-Item C:\TempScannerLogs\HS-*
}
