function stoplp() {
   Stop-Service -DisplayName HS-AOPFrontDesk
   Stop-Service -DisplayName HS-Crestone1LabelPrinter
   Stop-Service -DisplayName HS-Crestone2LabelPrinter
   Stop-Service -DisplayName HS-MARCLabelPrinter
   Stop-Service -DisplayName HS-MedicalCenterLabelPrinter
   Stop-Service -DisplayName HS-Suite200LabelPrinter
   Stop-Service -DisplayName HS-YFSDUTCHCLARKLabelPrinter
   Stop-Service -DisplayName HS-YFSFrontDeskLabelPrinter
   Stop-Service -DisplayName HS-EDUCATIONALSERVICES
   Stop-Service -DisplayName HS-CWCFrontDeskLabelPrinter
   Remove-Item C:\TempScannerLogs\HS-*
}
