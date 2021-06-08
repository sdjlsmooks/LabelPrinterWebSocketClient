function bouncelp() {
   Stop-Service -DisplayName HS-AOPFrontDesk
   Stop-Service -DisplayName HS-CARETEAM
   Stop-Service -DisplayName HS-Crestone1LabelPrinter
   Stop-Service -DisplayName HS-Crestone2LabelPrinter
   Stop-Service -DisplayName HS-CWCFrontDeskLabelPrinter
   Stop-Service -DisplayName HS-DiscoveryCenter
   Stop-Service -DisplayName HS-EDUCATIONALSERVICES
   Stop-Service -DisplayName HS-LearningCenter
   Stop-Service -DisplayName HS-MARCLabelPrinter
   Stop-Service -DisplayName HS-MedicalCenterLabelPrinter
   Stop-Service -DisplayName HS-RecoveryCenter
   Stop-Service -DisplayName HS-Suite200LabelPrinter
   Stop-Service -DisplayName HS-YFSDUTCHCLARKLabelPrinter
   Stop-Service -DisplayName HS-YFSFrontDeskLabelPrinter
   Remove-Item C:\TempScannerLogs\HS-*
   Restart-Service -Name Spooler -Force
   Start-Service -DisplayName HS-AOPFrontDesk
   Start-Service -DisplayName HS-CARETEAM
   Start-Service -DisplayName HS-Crestone1LabelPrinter
   Start-Service -DisplayName HS-Crestone2LabelPrinter
   Start-Service -DisplayName HS-CWCFrontDeskLabelPrinter
   Start-Service -DisplayName HS-DiscoveryCenter
   Start-Service -DisplayName HS-EDUCATIONALSERVICES
   Start-Service -DisplayName HS-LearningCenter
   Start-Service -DisplayName HS-MARCLabelPrinter
   Start-Service -DisplayName HS-MedicalCenterLabelPrinter
   Start-Service -DisplayName HS-RecoveryCenter
   Start-Service -DisplayName HS-Suite200LabelPrinter
   Start-Service -DisplayName HS-YFSDUTCHCLARKLabelPrinter
   Start-Service -DisplayName HS-YFSFrontDeskLabelPrinter
}
